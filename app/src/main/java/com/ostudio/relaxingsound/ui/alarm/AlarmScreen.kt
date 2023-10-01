package com.ostudio.relaxingsound.ui.alarm

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ostudio.relaxingsound.alarm.AlarmFunctions
import com.ostudio.relaxingsound.ui.utils.convertMillisToCustomFormat


@Composable
fun AlarmScreen(

) {
    val alarmFunctions = AlarmFunctions(context = LocalContext.current)

    val random = (1..100000) // 1~100000 범위에서 알람코드 랜덤으로 생성
    val alarmCode = random.random()

    Text(text = "알람")
    val currentTimeMillis = System.currentTimeMillis().plus(10000)
    // 함수를 사용하여 변환
    val formattedTime = convertMillisToCustomFormat(currentTimeMillis)
    val time = formattedTime

    Button(onClick = {
        setAlarm(
            alarmFunctions,
            alarmCode,
            "content",
            time,
        )
    }) {

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