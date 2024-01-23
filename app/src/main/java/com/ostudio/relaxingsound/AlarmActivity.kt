package com.ostudio.relaxingsound

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ostudio.relaxingsound.alarm.AlarmService
import com.ostudio.relaxingsound.music.RelaxingSoundService
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val test1 = intent?.extras?.getString("state")

        val serviceIntent = Intent(this@AlarmActivity, RelaxingSoundService::class.java)
        serviceIntent.putExtra("state", test1)

        setContent {
            RelaxingSoundTheme {
                AlarmActivityComposableScreen(onClick = {
                    this.startService(serviceIntent)
                })

            }
        }


    }
}

@Composable
fun AlarmActivityComposableScreen(onClick: () -> Unit = {}) {
    Button(onClick = { onClick.invoke() }) {
        Text(text = "알람 종료")
    }
}