package com.ostudio.relaxingsound.toast


enum class ToastDuration {
    Short,
    Default,
    Long,
    Indefinite,
}

fun ToastDuration.toMillis(): Long {
    return when (this) {
        ToastDuration.Short -> 1000L
        ToastDuration.Default -> 3000L
        ToastDuration.Long -> 5000L
        ToastDuration.Indefinite -> Long.MAX_VALUE
    }
}