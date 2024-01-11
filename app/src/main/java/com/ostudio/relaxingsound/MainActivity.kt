package com.ostudio.relaxingsound

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.media.recorder.ScreenRecorder
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    private val viewModel: AudioViewModel by viewModels()
    //https://github.com/Hoodlab/Jet_Audio
    lateinit var alarmManager: AlarmManager
    lateinit var pendingintent: PendingIntent

    private val screenRecorder by lazy {
        ScreenRecorder(this)
    }


    fun writeTextToFile() {
        val filePath = cacheDir.absolutePath + "/myText.txt"
        Log.d("Test", "path : $filePath")
        val file = File(filePath)
        val fileWriter = FileWriter(file, false)
        val bufferedWriter = BufferedWriter(fileWriter)

        bufferedWriter.append("Test1\n")
        bufferedWriter.append("Test2")
        bufferedWriter.newLine()
        bufferedWriter.append("Test3\n")
        bufferedWriter.close()
    }

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
//        writeTextToFile()
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//
//        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.putExtra("state", "alarm on")
//        pendingintent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
//
//        alarmManager.set(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis() + 3000L,
//            pendingintent
//        )

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

//    private fun startRecording() {
//        screenRecorder.startRecording()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode) {
//            ScreenRecorder.SCREEN_RECORD_REQUEST_CODE -> {
//                startRecording()
//            }
//        }
//    }
}