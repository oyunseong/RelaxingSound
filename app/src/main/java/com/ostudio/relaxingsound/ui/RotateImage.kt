package com.ostudio.relaxingsound.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource

@Composable
fun InfiniteRotatingImage(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    durationMillis: Int = 1500,
    easing: Easing = LinearEasing,
    repeatMode: RepeatMode = RepeatMode.Restart,
    label: String = "",
) {
    var isRotating by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        isRotating = true
    })

    val rotationState by animateFloatAsState(
        targetValue = if (isRotating) 360f else 0f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = durationMillis,
                easing = easing
            ),
            repeatMode = repeatMode
        ),
        label = label
    )

    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .graphicsLayer(rotationZ = rotationState)
    )
}