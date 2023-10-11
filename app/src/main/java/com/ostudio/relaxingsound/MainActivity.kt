package com.ostudio.relaxingsound

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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