package com.ostudio.relaxingsound

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RelaxingSoundTheme {
                RelaxingSoundApp(
                    snackbarManager = SnackbarManager(lifecycleScope),
                    onFinished = {
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