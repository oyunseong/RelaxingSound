package com.ostudio.relaxingsound.toast

import kotlin.random.Random

data class ToastMessage(
    val id: Long = createUniqueId(),
    val message: String = "",
    val type: ToastMessageType = ToastMessageType.NONE,
    val duration: ToastDuration = ToastDuration.Default,
    val isVisible: Boolean = true
)

enum class ToastMessageType {
    SUCCESS, ERROR, WARNING, NONE
}

private fun createUniqueId(): Long {
    val random = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
    return System.currentTimeMillis() + random
}
