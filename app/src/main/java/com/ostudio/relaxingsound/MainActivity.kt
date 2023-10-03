package com.ostudio.relaxingsound

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelaxingSoundTheme {
                RelaxingSoundApp(onFinished = {
                    finish()
                })
            }
        }
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("++##", it)
        }
    }

}