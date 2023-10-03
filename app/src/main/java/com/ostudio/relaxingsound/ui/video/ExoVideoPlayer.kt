package com.ostudio.relaxingsound.ui.video

import android.content.Context
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.File

@Composable
fun ExoVideoPlayer() {
    val context = LocalContext.current
    val exoPlayer = remember {getSimpleExoPlayer(context)}
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .background(color = Color.Yellow),
        factory = { context1 ->
            PlayerView(context1).apply {
                player = exoPlayer
            }
        },
    )
}
private fun getSimpleExoPlayer(context: Context): SimpleExoPlayer {
    return SimpleExoPlayer.Builder(context).build().apply {
        val dataSourceFactory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )
        //local video
//        val localVideoItem = MediaItem.fromUri(file.toUri())
//        val localVideoSource = ProgressiveMediaSource
//            .Factory(dataSourceFactory)
//            .createMediaSource(localVideoItem)
//        this.addMediaSource(localVideoSource)

        // streaming from internet
        val internetVideoItem = MediaItem.fromUri("https://www.youtube.com/watch?v=8B31uWJJ20U&t=473s")
        val internetVideoSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(internetVideoItem)
        this.addMediaSource(internetVideoSource)
        // init
        this.prepare()
    }
}
@Composable
fun <T : View> AndroidView(
    factory: (Context) -> T,
    modifier: Modifier = Modifier,
    update: (T) -> Unit = NoOpUpdate
) {}