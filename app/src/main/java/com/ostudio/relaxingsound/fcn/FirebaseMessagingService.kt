package com.ostudio.relaxingsound.fcn

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ostudio.relaxingsound.MainActivity
import com.ostudio.relaxingsound.R


class FirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var builder: NotificationCompat.Builder
    private val TAG = "FirebaseMessagingService"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // token을 서버로 전송
        registerTokenOnLocal(token)
        registerTokenOnServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.data.isNotEmpty()) {
            sendNotification(message)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시
        val uniqueId: Int = (System.currentTimeMillis() / 7).toInt()
        // 일회용 pendingIntent : intent의 실행 권한을 외부의 어플리케이션에게 위임
        val intent = Intent(this, MainActivity::class.java)

        for (key in remoteMessage.data.keys) {
            intent.putExtra(key, remoteMessage.data.getValue(key))
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // activity stack 을 경로만 남김 ( A->B->C->D->B => A->B )

        val pendingIntent = PendingIntent.getActivity(
            this,
            uniqueId,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보, 작업
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(remoteMessage.data["title"].toString()) // 제목
            .setContentText(remoteMessage.data["body"].toString()) // 메시지 내용
            .setAutoCancel(true) // 알람클릭시 삭제여부
            .setSound(soundUri)  // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniqueId, notificationBuilder.build())
    }

    private fun registerTokenOnServer(token: String) {
        // 서버로 토큰 전송
    }

    // 로컬에 토큰 저장
    private fun registerTokenOnLocal(token: String) {
        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("token", token).apply()
        editor.commit()
        Log.i(TAG, "success save token")
    }


    fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("++##", it)
        }
    }

    companion object {
        const val CHANNEL_ID = "RELAXING_FCM_CHANNEL_ID"
        const val CHANNEL_NAME = "RELAXING_FCM_CHANNEL"
    }

}