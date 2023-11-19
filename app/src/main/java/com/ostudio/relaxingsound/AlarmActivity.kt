package com.ostudio.relaxingsound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ostudio.relaxingsound.ui.theme.RelaxingSoundTheme

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelaxingSoundTheme {
                AlarmActivityComposableScreen()
            }
        }
    }
}

@Composable
fun AlarmActivityComposableScreen() {
    Button(onClick = { }) {
        Text(text = "알람 종료")
    }
}