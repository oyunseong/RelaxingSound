package com.ostudio.relaxingsound.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch


object SnackbarManager {
    private val messages: Channel<SnackbarMessage> = Channel()
    val snackbar : MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        coroutineScope.launch {
            messages.consumeAsFlow().collect {
                snackbar.emit(it)
                delay(3000L)
                snackbar.emit(it.copy(isVisible = false))
                delay(500L)
                snackbar.emit(null)
                delay(100L)
            }
        }
    }

    fun showMessage(snackbarMessage: SnackbarMessage) {
        coroutineScope.launch {
            messages.send(snackbarMessage)
        }
    }
}