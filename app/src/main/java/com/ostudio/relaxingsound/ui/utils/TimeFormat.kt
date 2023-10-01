package com.ostudio.relaxingsound.ui.utils

import java.text.SimpleDateFormat
import java.util.Date

fun convertMillisToCustomFormat(millis: Long): String {
    // 1. Milliseconds를 Date 객체로 변환
    val date = Date(millis)

    // 2. SimpleDateFormat을 사용하여 원하는 형식으로 포맷
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(date)
}