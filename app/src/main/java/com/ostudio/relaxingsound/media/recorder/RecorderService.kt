package com.ostudio.relaxingsound.media.recorder

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RecorderService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}