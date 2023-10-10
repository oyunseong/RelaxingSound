package com.ostudio.relaxingsound.ui.alarm

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ostudio.relaxingsound.alarm.AlarmFunctions
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import com.ostudio.relaxingsound.snackbar.SnackbarMessageType
import com.ostudio.relaxingsound.ui.home.createSnackbarMessage
import com.ostudio.relaxingsound.ui.utils.convertMillisToCustomFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Composable
fun AlarmScreen() {
    val alarmFunctions = AlarmFunctions(context = LocalContext.current)

    val random = (1..100000) // 1~100000 범위에서 알람코드 랜덤으로 생성
    val alarmCode = random.random()
    val error: MutableStateFlow<Throwable> = remember { MutableStateFlow(Throwable()) }

    val currentTimeMillis = System.currentTimeMillis().plus(10000)
    // 함수를 사용하여 변환
    val formattedTime = convertMillisToCustomFormat(currentTimeMillis)
    val time = formattedTime
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        error.collect {
            when (it) {
                is NullPointerException -> {
                    SnackbarManager.showMessage(
                        SnackbarMessage(
                            message = "Null pointer Exception",
                            type = SnackbarMessageType.ERROR,
                        )
                    )
                }

                else -> {
                    SnackbarManager.showMessage(
                        SnackbarMessage(
                            message = "알 수 없는 오류",
                            type = SnackbarMessageType.ERROR,
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
            Text(text = "푸시 알람")
        }

        Button(onClick = {
            scope.launch {
                createSnackbarMessage(
                    location = "Alarm"
                )
            }
        }) {
            Text(text = "클릭")
        }

        // Exception 발생 시키는 버튼
        Button(onClick = { createException(error) }) {
            Text(text = "예외")
        }

        var offset by remember { mutableStateOf(0.dp) }

        Button(
            onClick = {
                // 버튼을 클릭할 때마다 offset을 10.dp 증가시킵니다.
                offset += 10.dp
            },
            modifier = Modifier
                .padding(16.dp)
                .offset(x = animateDpAsState(targetValue = offset, animationSpec = tween(),
                    label = ""
                ).value)
        ) {
            Text(text = "애니메이션")
        }
    }


}

// 예외 케이스 생성 함수
fun createException(
    error: MutableStateFlow<Throwable>,
    exception: Exception = NullPointerException()
) {
    val scope = CoroutineScope(Dispatchers.Main)
    try {
        throw exception
    } catch (e: Exception) {
        scope.launch {
            error.emit(e)
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