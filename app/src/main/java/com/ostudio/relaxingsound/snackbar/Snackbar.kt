package com.ostudio.relaxingsound.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ostudio.relaxingsound.R
import com.ostudio.relaxingsound.ui.theme.Gray600
import com.ostudio.relaxingsound.ui.theme.snackbarFontStyle
import kotlinx.coroutines.delay


@Composable
fun Snackbar(message: SnackbarMessage) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = message, block = {
        when (message.duration) {
            SnackbarDuration.Indefinite -> {
                isVisible = true
            }

            else -> {
                isVisible = true
                delay(message.duration.toMillis() - 300L)
                isVisible = false
            }

        }

    })

    SnackbarContent(
        message = message.message,
        type = message.type,
        location = Alignment.BottomCenter,
        isVisible = isVisible
    )
}

@Composable
private fun SnackbarContent(
    modifier: Modifier = Modifier,
    message: String = "",
    type: SnackbarMessageType = SnackbarMessageType.NONE,
    location: Alignment = Alignment.BottomCenter,
    isVisible: Boolean = false,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(location),
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Card(
                modifier = Modifier
                    .align(location)
                    .padding(horizontal = 10.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Gray600, // Color(0xFF4B5563)
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                    ) {
                        SnackbarIcon(type = type)
                        if (type != SnackbarMessageType.NONE) {
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Text(
                            text = message,
                            color = Color.White,
                            style = snackbarFontStyle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun SnackbarIcon(type: SnackbarMessageType) {
    when (type) {
        SnackbarMessageType.SUCCESS -> {
            Image(
                modifier = Modifier.size(24.dp),
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_check_circle)
            )
        }

        SnackbarMessageType.ERROR -> {
            Image(
                modifier = Modifier.size(24.dp),
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_error_circle)
            )
        }

        SnackbarMessageType.WARNING -> {
            Image(
                modifier = Modifier.size(24.dp),
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_warning)
            )
        }

        SnackbarMessageType.NONE -> {}
    }
}

enum class SnackbarMessageType {
    SUCCESS, ERROR, WARNING, NONE
}

@Preview
@Composable
private fun PreviewSnackBarContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarContent(
            message = "확인 멘트가 표기됩니다.확인 멘트가 표기됩니다.확인 멘트가 표기됩니다.확인 멘트가 표기됩니다.",
            type = SnackbarMessageType.WARNING
        )
    }
}