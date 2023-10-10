package com.ostudio.relaxingsound.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue


object SnackbarManager {
    private val messages: Queue<SnackbarMessage> = LinkedList()
    val message: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun showMessage(snackbarMessage: SnackbarMessage) {
        messages.offer(snackbarMessage)
        if (message.value == null) {
            showNextMessage()
        }
    }

    fun showMessage(
        title: String,
        type: SnackbarMessageType = SnackbarMessageType.NONE,
        duration: SnackbarDuration = SnackbarDuration.Default,
    ) {
        messages.offer(
            SnackbarMessage(
                message = title,
                type = type,
                duration = duration
            )
        )
        if (message.value == null) {
            showNextMessage()
        }
    }


    /**
     * 메시지 큐에서 메시지를 꺼내는 함수
     *
     * 1. 메시지 큐에 새로운 메시지가 추가되면 메시지를 꺼냅니다,
     * 2. 메시지가 지정된 지속 시간 동안 표시된 후에는 메시지를 감춥니다.
     * 3. 메시지가 감춰진 후에는 재귀적으로 자신을 호출하여 큐에서 다음 메시지를 표시합니다.
     */
    private fun showNextMessage() {
        coroutineScope.launch {
            val currentMessage = messages.poll()

            if (currentMessage != null) {
                // 꺼낸 메시지를 UI에 표시합니다.
                message.emit(currentMessage)

                // 메시지의 지속 시간 동안 대기합니다.
                delay(currentMessage.duration.toMillis())
                // 메시지를 감춥니다.
                message.emit(null)

                // 재귀적으로 자신을 호출하여 큐에서 다음 메시지를 표시
                showNextMessage()
            }
        }
    }

    fun clear() {
        messages.clear()
    }

}