package com.ostudio.relaxingsound.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.ostudio.relaxingsound.AlarmActivity
import kotlin.math.absoluteValue


/**
 * AlarmReceiver가 호출할 Service 클래스
 */
class AlarmService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val test1 = intent?.extras?.getString("state")

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, AlarmActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}