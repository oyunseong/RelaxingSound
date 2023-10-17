package com.ostudio.relaxingsound.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ostudio.relaxingsound.NetworkManager
import com.ostudio.relaxingsound.snackbar.SnackbarDuration
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import com.ostudio.relaxingsound.snackbar.SnackbarMessageType
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "홈")
        Button(onClick = {
            scope.launch {
                createSnackbarMessage()
            }
        }) {
            Text(text = "클릭!")
        }
        Button(onClick = {
            checkNetwork()
        }) {
            Text(text = "네트워크")
        }
//        ExoVideoPlayer()
    }
}

fun checkNetwork() {
    if(NetworkManager.isNetworkAvailable.value){
        Log.d("++##", "네트워크 활성화")
    }else{
        Log.d("++##", "네트워크 비활성화")
    }
}

@Composable
fun ExoplayerExample() {
//
//    val context = LocalContext.current
//
//    val mediaItem = MediaItem.Builder()
//        .setUri("your-uri")
//        .build()
//    val exoPlayer = remember(context, mediaItem) {
//        ExoPlayer.Builder(context)
//            .build()
//            .also { exoPlayer ->
//                exoPlayer.setMediaItem(mediaItem)
//                exoPlayer.prepare()
//                exoPlayer.playWhenReady = false
//                exoPlayer.repeatMode = REPEAT_MODE_OFF
//            }
//    }
//
//    DisposableEffect(
//        AndroidView(factory = {
//            StyledPlayerView(context).apply {
//                player = exoPlayer
//            }
//        })
//    ) {
//        onDispose { exoPlayer.release() }
//    }


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