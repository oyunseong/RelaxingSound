package com.ostudio.relaxingsound.snackbar

import kotlinx.coroutines.flow.MutableStateFlow


object SnackbarManager {
    val snackbar: MutableStateFlow<List<SnackbarMessage>> = MutableStateFlow(emptyList())

    fun showMessage(snackbarMessage: SnackbarMessage) {
        snackbar.value += snackbarMessage
    }

    fun clearIfAllisInvisible() {
        val isAllHidden = snackbar.value.none { it.isVisible }
        if (isAllHidden) {
            snackbar.value = emptyList()
        }
    }

}