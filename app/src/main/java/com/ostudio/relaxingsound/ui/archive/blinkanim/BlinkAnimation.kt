package com.ostudio.relaxingsound.ui.archive.blinkanim

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ostudio.relaxingsound.R
import kotlinx.coroutines.delay


/**
 * 이미지가 로딩중일 때 보여줄만한 애니메이션
 * N초간 깜빡임.
 * 이미지 클릭 시, 깜빡임 재시작
 */
@Composable
fun BoxScope.ImageLoadingAnim(
    isVisible: Boolean,
    onClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 500
                0.7f at 1000
            },
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    if (isVisible) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.Yellow)
                .clickable {
                    onClick.invoke()
                },
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = ""
        )
    } else {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .alpha(alpha)
                .background(color = Color.Gray)
        )
    }
}

@Preview
@Composable
private fun PreviewBlinkAnimation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        var isVisible by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = isVisible, block = {
            delay(30000L)
            isVisible = true
        })

        ImageLoadingAnim(
            isVisible = isVisible,
            onClick = {
                isVisible = false
            })
    }
}