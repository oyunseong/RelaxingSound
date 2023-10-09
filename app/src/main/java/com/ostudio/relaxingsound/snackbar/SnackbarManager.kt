package com.ostudio.relaxingsound.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue


/**
 * 예외처리 고민해봐야함.
 */
class SnackbarManager(
    private val coroutineScope : CoroutineScope
) {
    private val messages: MutableStateFlow<Queue<SnackbarMessage>> = MutableStateFlow(LinkedList())
    val message: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
//    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun showMessage(snackbarMessage: SnackbarMessage) {
        synchronized(messages) {
            messages.value.offer(snackbarMessage)
            if (message.value == null) {
                showNextMessage()
            }
        }
    }

    private fun showNextMessage() {
        coroutineScope.launch {
            val currentMessage = messages.value.poll()
            if (currentMessage != null) {
                message.value = currentMessage
                delay(currentMessage.duration)
                message.value = null
                showNextMessage()
            }
        }
    }
}