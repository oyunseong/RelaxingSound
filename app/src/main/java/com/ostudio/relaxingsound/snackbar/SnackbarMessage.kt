package com.ostudio.relaxingsound.snackbar

import kotlin.random.Random


data class SnackbarMessage(
    val id: Long = createUniqueId(),
    val message: String = "",
    val type: SnackbarMessageType = SnackbarMessageType.NONE,
    val duration: SnackbarDuration = SnackbarDuration.Default,
    val isVisible: Boolean = false
)

private fun createUniqueId(): Long {
    val random = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
    return System.currentTimeMillis() + random
}


