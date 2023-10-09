package com.ostudio.relaxingsound.snackbar


data class SnackbarMessage(
    val message: String = "",
    val type: SnackbarMessageType = SnackbarMessageType.NONE,
    val duration: Long = 0L,
)

