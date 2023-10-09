package com.ostudio.relaxingsound.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import com.ostudio.relaxingsound.snackbar.SnackbarMessageType
import com.ostudio.relaxingsound.ui.video.ExoVideoPlayer

@Composable
fun HomeScreen(
    snackbarManager: SnackbarManager// = SnackbarManager()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "홈")
        Button(onClick = { createSnackbarMessage(snackbarManager) }) {
            Text(text = "클릭!")
        }
//        ExoVideoPlayer()
    }

}

var count = 0
fun createSnackbarMessage(snackbarManager: SnackbarManager, location: String = "Home") {
    count += 1
    val snackbarMessage =
        SnackbarMessage(
            message = "Test Message $count from $location",
            type = SnackbarMessageType.SUCCESS,
            duration = 2000L
        )

    snackbarManager.showMessage(snackbarMessage = snackbarMessage)
}

fun createExceptionMessage(
    snackbarManager: SnackbarManager,
    content: String = "",
    location: String = "Home"
) {
    val snackbarMessage =
        SnackbarMessage(
            message = content,
            type = SnackbarMessageType.ERROR,
            duration = 2000L
        )

    snackbarManager.showMessage(snackbarMessage = snackbarMessage)
}

@Preview
@Composable
private fun PreviewHomeScreen() {
//    HomeScreen()
}