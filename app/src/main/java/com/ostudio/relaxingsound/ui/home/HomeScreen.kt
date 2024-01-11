package com.ostudio.relaxingsound.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ostudio.relaxingsound.NetworkManager
import com.ostudio.relaxingsound.R
import com.ostudio.relaxingsound.snackbar.SnackbarDuration
import com.ostudio.relaxingsound.snackbar.SnackbarManager
import com.ostudio.relaxingsound.snackbar.SnackbarMessage
import com.ostudio.relaxingsound.snackbar.SnackbarMessageType
import com.ostudio.relaxingsound.ui.InfiniteRotatingImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()

    var progress by remember { mutableStateOf(false) }
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

        Button(onClick = {
            progress = !progress
        }) {
            Text(text = "프로그래스바")
        }
        if (progress) {
            InfiniteRotatingImage(image = R.drawable.img_loader)
//            RotateImage()
        }
    }
}

fun checkNetwork() {
    if (NetworkManager.isNetworkAvailable.value) {
        Log.d("++##", "네트워크 활성화")
    } else {
        Log.d("++##", "네트워크 비활성화")
    }
}

@Composable
fun RotateImage1() {
    var rotationState by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            Log.d("++##", "while")
            withContext(Dispatchers.Main) {
                rotationState += 2f // Adjust the rotation speed as needed
            }
            delay(16) // 60 frames per second
        }
    }

    Image(
        painter = painterResource(id = R.drawable.img_loader),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer(rotationZ = rotationState)
    )
}

@Composable
fun progressBar() {
    val ctx = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(ctx)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${ctx.applicationContext.packageName}/${R.raw.img_progress_anim}")
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()


    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
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