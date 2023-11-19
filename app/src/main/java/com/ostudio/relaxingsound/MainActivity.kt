package com.ostudio.relaxingsound

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.alarm.AlarmReceiver
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme


//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    private val viewModel: AudioViewModel by viewModels()
    //https://github.com/Hoodlab/Jet_Audio
    lateinit var alarmManager: AlarmManager
    lateinit var pendingintent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelaxingSoundTheme {
                RelaxingSoundApp(
                    onFinished = {
                        finish()
                    })
            }
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("state", "alarm on")
        pendingintent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 3000L,
            pendingintent
        )

        NetworkManager.initialize(this)

        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("++##", it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        SnackbarManager.clear()
    }

}