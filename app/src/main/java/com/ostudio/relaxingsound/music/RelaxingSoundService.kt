package com.ostudio.relaxingsound.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.ostudio.relaxingsound.R

class RelaxingSoundService : Service(), MediaPlayer.OnPreparedListener {
    private var mediaPlayer: MediaPlayer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("++##","onStartCommand")
        mediaPlayer = MediaPlayer.create(this, com.google.firebase.messaging.R.raw.firebase_common_keep)
        mediaPlayer?.apply {
            setOnPreparedListener(this@RelaxingSoundService)
            prepareAsync()
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer?.start()
        Log.d("++##","시작")
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        Log.d("++##","파괴")
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer?.start()
        Log.d("++##","onPrepared")
    }
}