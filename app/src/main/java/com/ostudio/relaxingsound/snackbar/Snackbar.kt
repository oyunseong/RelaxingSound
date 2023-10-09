package com.ostudio.relaxingsound.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ostudio.relaxingsound.R
import com.ostudio.relaxingsound.ui.theme.Gray600


@Composable
fun Snackbar(snackbarData: SnackbarMessage) {
    SnackbarContent(
        message = snackbarData.message,
        type = snackbarData.type,
//        location = TODO 추가
    )
}

@Composable
private fun SnackbarContent(
    modifier: Modifier = Modifier,
    message: String = "",
    type: SnackbarMessageType = SnackbarMessageType.NONE,
    location: Alignment = Alignment.BottomCenter
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .fillMaxWidth()
                .height(60.dp)
                .align(location),
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
//                        style = body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
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
