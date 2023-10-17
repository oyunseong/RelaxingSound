package com.ostudio.relaxingsound.ui.archive.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ostudio.relaxingsound.ui.theme.Gray400
import com.ostudio.relaxingsound.ui.theme.Gray600
import com.ostudio.relaxingsound.ui.theme.Primary500
import com.ostudio.relaxingsound.ui.theme.caption2
import com.ostudio.relaxingsound.ui.theme.dpToPx
import com.ostudio.relaxingsound.ui.theme.dpToSp
import kotlin.math.absoluteValue

@Composable
fun SuperSimpleLineChartWithLabels(
    values: List<Point>,
    nWeeks: Int,
    maxY: Int,
    modifier: Modifier = Modifier
) {
    var maxWidth by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier
//            .border(width = 1.dp, color = Color.Black)
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 13.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0 until maxY) {
                    Row() {
                        Box(
                            modifier = Modifier
                                .width(9.dp)
                                .height(15.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                style = caption2,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = i.toString(),
                                color = Gray400
                            )
                        }
                        GrayLine(
                            modifier = Modifier
                                .padding(start = 18.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 21.dp),
            ) {
                for (i in 0 until nWeeks) {
                    Text(
                        modifier = Modifier.weight(1f), text = "${i + 1}주",
                        style = caption2,
                        color = Gray400
                    )
                }
                Text(
                    text = "n주",
                    style = caption2,
                    color = Gray400
                )
            }
            SuperSimpleLineChart(
                values = values,
                nWeeks = nWeeks,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxSize()
                    .padding(start = 29.dp, bottom = 21.dp)
//                    .background(color = Color(0x90000000))
            )
        }
    }
}

@Composable
fun SuperSimpleLineChart(
    values: List<Point>,
    nWeeks: Int,
    modifier: Modifier
) {

    Box(modifier = modifier
        .drawBehind {
            val minXValue = values.minOf { it.x }
            val maxXValue = values.maxOf { it.x }
            val minYValue = values.minOf { it.y }
            val maxYValue = values.maxOf { it.y }

            // Define the number of intervals between data points
            val intervals = nWeeks

            val pixelPoints = values.mapIndexed { index, point ->
                val xInterval = size.width.absoluteValue / intervals
                val x = index * xInterval

                val y = point.y.mapValueToDifferentRange(
                    inMin = minYValue,
                    inMax = maxYValue,
                    outMin = size.height,
                    outMax = 0f
                )
                Point(x, y)
            }

            val path = Path()
            pixelPoints.forEachIndexed { index, point ->
                if (index == 0) {
                    path.moveTo(point.x, point.y)
                } else {
                    path.lineTo(point.x, point.y)
                }
            }

            // point to point line
            drawPath(
                path = path,
                color = Primary500,
                style = Stroke(width = 3f)
            )

            // point indicator
            pixelPoints.forEach { point ->
                drawCircle(
                    color = Primary500,
                    center = Offset(point.x, point.y),
                    radius = 3.dp.toPx()
                )

                drawCircle(
                    color = Color.White,
                    center = Offset(point.x, point.y),
                    radius = 1.dp.toPx()
                )
            }
        })
}


data class Point(val x: Float, val y: Float)

// simple extension function that allows conversion between ranges
fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

@Composable
fun Card(content: @Composable ColumnScope.() -> Unit) {
    val cardShape = RoundedCornerShape(16.dp)
    androidx.compose.material3.Card(
        modifier = Modifier
            .shadow(
                shape = cardShape,
                spotColor = Color(0x00000000),
                elevation = 24.dp
            ),
        shape = cardShape,
        content = content,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    )
}

@Composable
fun GrayLine(
    modifier: Modifier = Modifier,
    height: Dp = 0.5.dp
) {
    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(color = Color(0xFFE5E7EB))
    )
}

@Preview(showBackground = true, backgroundColor = 0x0000000)
@Composable
private fun PreviewLinearLineGraph() {
    val values = listOf(
        Point(0f, 0f),
        Point(1f, 3f),
        Point(2f, 2f),
        Point(3f, 2f),
        Point(4f, 3f),
        Point(5f, 4f)
    )

    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "무릎 아래에 손가락이 5개 정도 들어가고, \u2028무릎이 조금 구부러져 있어요")
            Spacer(modifier = Modifier.height(24.dp))
            SuperSimpleLineChartWithLabels(values = values, nWeeks = 4, maxY = 5)
        }

    }

}
