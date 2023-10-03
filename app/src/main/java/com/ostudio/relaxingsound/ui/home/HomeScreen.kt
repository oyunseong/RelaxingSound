package com.ostudio.relaxingsound.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ostudio.relaxingsound.ui.video.ExoVideoPlayer

@Composable
fun HomeScreen(

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "홈")
        ExoVideoPlayer()
    }

}