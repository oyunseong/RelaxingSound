package com.ostudio.relaxingsound.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ostudio.relaxingsound.alarm.AlarmFunctions
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import com.ostudio.relaxingsound.snackbar.SnackbarMessageType
import com.ostudio.relaxingsound.ui.home.createExceptionMessage
import com.ostudio.relaxingsound.ui.home.createSnackbarMessage
import com.ostudio.relaxingsound.ui.utils.convertMillisToCustomFormat
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.NullPointerException


@Composable
fun AlarmScreen(
    snackbarManager: SnackbarManager
) {
    val alarmFunctions = AlarmFunctions(context = LocalContext.current)

    val random = (1..100000) // 1~100000 범위에서 알람코드 랜덤으로 생성
    val alarmCode = random.random()
    val error: MutableStateFlow<Throwable> = remember { MutableStateFlow(Throwable()) }

    Text(text = "알람")
    val currentTimeMillis = System.currentTimeMillis().plus(10000)
    // 함수를 사용하여 변환
    val formattedTime = convertMillisToCustomFormat(currentTimeMillis)
    val time = formattedTime

    LaunchedEffect(key1 = Unit, block = {
        error.collect {
            when (it) {
                is NullPointerException -> {
                    snackbarManager.showMessage(
                        SnackbarMessage(
                            message = "Null pointer",
                            type = SnackbarMessageType.ERROR,
                            duration = 2000L
                        )
                    )
                }

                else -> {
                    snackbarManager.showMessage(
                        SnackbarMessage(
                            message = "알 수 없는 오류",
                            type = SnackbarMessageType.ERROR,
                            duration = 2000L
                        )
                    )
                }

            }

        }
    })

    Column {
        Button(onClick = {
            setAlarm(
                alarmFunctions,
                alarmCode,
                "content",
                time,
            )
        }) {
            Text(text = "알람")
        }

        Button(onClick = {
            createSnackbarMessage(
                snackbarManager = snackbarManager,
                location = "Alarm"
            )
        }) {
            Text(text = "클릭")
        }

        Button(onClick = { throw NullPointerException() }) {
            Text(text = "에러")
        }
    }


}

private fun setAlarm(
    alarmFunctions: AlarmFunctions,
    alarmCode: Int,
    content: String,
    time: String
) {
    alarmFunctions.callAlarm(time, alarmCode, content)
}