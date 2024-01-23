package com.ostudio.relaxingsound.toast

import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


object StackToastManager {
    val toast: MutableStateFlow<List<SnackbarMessage>> = MutableStateFlow(emptyList())

    fun showMessage(toastMessage: SnackbarMessage) {
        this.toast.value += toastMessage
    }

    fun clearMessage(id: Long) {
        toast.update { toast.value.filter { it.id != id } }
    }

    fun searchDuplicatedMessage(message: String): SnackbarMessage? {
        return toast.value.find { it.message == message }
    }
}

fun showToast(
    message: String,
) {
    val duplicateMessage = StackToastManager.searchDuplicatedMessage(message = message)

    if (duplicateMessage == null) {
        val toastMessage = SnackbarMessage(
            message = message,
        )
        StackToastManager.showMessage(toastMessage)
    } else {
        StackToastManager.clearMessage(duplicateMessage.id)
        val toastMessage = SnackbarMessage(
            message = message,
        )
        StackToastManager.showMessage(toastMessage)
    }
}