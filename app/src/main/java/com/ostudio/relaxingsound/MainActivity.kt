package com.ostudio.relaxingsound

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme

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