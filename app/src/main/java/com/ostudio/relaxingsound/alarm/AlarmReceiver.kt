package com.ostudio.relaxingsound.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ostudio.relaxingsound.R

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var manager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    // 오레오 이상 버전은 채널을 설정 해야 Notification이 동작함.
    companion object {
        const val CHANNEL_ID = "channel"
        const val CHANNEL_NAME = "channel1"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )

        builder = NotificationCompat.Builder(context, CHANNEL_ID)

        val alarmServiceIntent = Intent(context, AlarmService::class.java)
        val requestCode = intent?.extras!!.getInt("Alarm_requestCode")
        val title = intent.extras!!.getString("content")

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                requestCode,
                alarmServiceIntent,
                PendingIntent.FLAG_IMMUTABLE
            ); //Activity를 시작하는 인텐트 생성
        } else {
            PendingIntent.getActivity(
                context,
                requestCode,
                alarmServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val notification = builder.setContentTitle(title)
            .setContentText("SCHEDULE MANAGER") // 알림 제목
            .setSmallIcon(R.drawable.ic_launcher_background)    // 본문
            .setAutoCancel(true)    // 알림을 탭사면 삭제 가능 여부
            .setContentIntent(pendingIntent)    // 알림을 눌렀을 때 실행할 작업 인텐트 설정
            .build()

        manager.notify(1, notification)
    }
}