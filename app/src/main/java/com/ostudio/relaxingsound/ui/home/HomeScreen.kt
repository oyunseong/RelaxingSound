package com.ostudio.relaxingsound.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ostudio.relaxingsound.snackbar.SnackbarDuration
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import com.ostudio.relaxingsound.snackbar.SnackbarMessageType
import com.ostudio.relaxingsound.ui.video.ExoVideoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "홈")
        Button(onClick = {
            scope.launch {
                createSnackbarMessage()
            }
        }) {
            Text(text = "클릭!")
        }
//        ExoVideoPlayer()
    }

}

var count = 0
suspend fun createSnackbarMessage(location: String = "Home") {
    count += 1
    val snackbarMessage =
        SnackbarMessage(
            message = "Test Message $count from $location",
            type = SnackbarMessageType.SUCCESS,
            duration = SnackbarDuration.Default
        )

    SnackbarManager.showMessage(snackbarMessage = snackbarMessage)
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}