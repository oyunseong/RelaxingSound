package com.ostudio.relaxingsound.ui.archive.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ostudio.relaxingsound.ui.theme.Primary500
import com.ostudio.relaxingsound.ui.theme.dpToPx

@Composable
fun SimpleBarChart(
    values: List<Point>,
    modifier: Modifier,
    barWidth: Dp = 20.dp
) {
    val outCircle = dpToPx(dp = 4f)
    val inCircle = dpToPx(dp = 2f)
    Box(modifier = modifier
        .padding(top = 8.dp)
        .drawBehind {
            values.forEachIndexed { index, point ->
                val x = point.x * size.width
                val y = size.height - point.y * size.height

                // Draw bar
                drawRect(
                    color = Primary500,
                    topLeft = Offset(x, size.height),
                    size = Size(barWidth.toPx(), y)
                )

                // Point indicator
                drawCircle(
                    color = Primary500,
                    center = Offset(x, y),
                    radius = outCircle
                )

                drawCircle(
                    color = Color.White,
                    center = Offset(x, y),
                    radius = inCircle
                )
            }
        }
    ) {

    }
}