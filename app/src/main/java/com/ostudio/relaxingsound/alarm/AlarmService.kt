package com.ostudio.relaxingsound.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder


/**
 * AlarmReceiver가 호출할 Service 클래스
 */
class AlarmService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }
}