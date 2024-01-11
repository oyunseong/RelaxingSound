package com.ostudio.relaxingsound.media.recorder

import android.app.Activity
import android.content.Context
import android.media.MediaRecorder
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.util.Log
import java.io.IOException

class ScreenRecorder(private val activity: Activity) {

    companion object {
        const val SCREEN_RECORD_REQUEST_CODE = 101
    }

    private val TAG = "ScreenRecorder"
    private var mediaRecorder: MediaRecorder? = null
    private var projectionManager: MediaProjectionManager? = null

    private fun createFileName(): String {
        return "${activity.cacheDir.absolutePath}/screenRecord_${System.currentTimeMillis()}"
    }

    private fun createMediaRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(activity)
        } else {
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(createFileName())
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        }
    }

    private fun startRecording() {
        //권한 요청
        projectionManager =
            activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        activity.startActivityForResult(
            projectionManager?.createScreenCaptureIntent(),
            SCREEN_RECORD_REQUEST_CODE
        )

        mediaRecorder = createMediaRecorder().apply {
            try {
                prepare()
            } catch (e: IOException) {
                Log.e(TAG, "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
}