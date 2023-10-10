package com.ostudio.relaxingsound.snackbar


enum class SnackbarDuration {
    Short,
    Default,
    Long,
    Indefinite,
}

fun SnackbarDuration.toMillis(): Long {
    return when (this) {
        SnackbarDuration.Short -> 1000L
        SnackbarDuration.Default -> 3000L
        SnackbarDuration.Long -> 5000L
        SnackbarDuration.Indefinite -> Long.MAX_VALUE
    }
}