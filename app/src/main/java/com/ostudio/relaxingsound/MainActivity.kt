package com.ostudio.relaxingsound

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: AudioViewModel by viewModels()
    //https://github.com/Hoodlab/Jet_Audio
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