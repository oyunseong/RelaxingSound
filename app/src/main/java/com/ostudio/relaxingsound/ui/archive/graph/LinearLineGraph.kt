package com.ostudio.relaxingsound.ui.archive.graph

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ostudio.relaxingsound.ui.theme.Gray400
import com.ostudio.relaxingsound.ui.theme.Primary500
import com.ostudio.relaxingsound.ui.theme.body1
import com.ostudio.relaxingsound.ui.theme.caption2
import com.ostudio.relaxingsound.ui.theme.dpToPx
import com.ostudio.relaxingsound.ui.theme.dpToSp
import com.ostudio.relaxingsound.ui.theme.pxToDp

data class AxisInfo(
    val minValue: Float,
    val maxValue: Float,
    val axisTick: List<AxisTick>,
)

data class AxisTick(val position: Int, val value: String)

@Composable
fun SimpleLineChartWithLabels(
    values: List<Point>,
    xAxisInfo: AxisInfo,
    yAxisInfo: AxisInfo,
    modifier: Modifier = Modifier,
) {
    var maxWidth by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(key1 = maxWidth, block = {
        Log.d("@@", "change maxWidth!! $maxWidth@@")
    })

    Column(
        modifier = modifier
            .height(89.dp)  // 입력으로 높이를 고정
            .fillMaxWidth()
//            .background(color = Color.Gray)
    ) {
        Box(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 13.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                YAxis(axisInfo = yAxisInfo, onSizeChangedText = {
                    Log.d("++##", "maxWidth change : $it")
                    if (maxWidth.width <= it.width) {
                        maxWidth = it
                    }
                })
            }
            XAxis(
                maxWidth = maxWidth,
                xAxisInfo = xAxisInfo
            )
            SimpleLineChart(
                values = values,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxSize()
                    .padding(start = 29.dp, bottom = 21.dp)
            )
        }
    }
    val a = pxToDp(pixels = 3f)
}


@Composable
fun SimpleLineChart(
    values: List<Point>,
    modifier: Modifier
) {
    val outCircle = dpToPx(dp = 4f)
    val inCircle = dpToPx(dp = 2f)
    Box(modifier = modifier
        .padding(top = 8.dp)
//        .background(color = Color(0x50FFFF00))
        .drawBehind {
            val result = values.map {
                it.copy(
                    x = it.x * size.width,
                    y = size.height - it.y * size.height
                )
            }

            val path = Path()
            result.forEachIndexed { index, point ->
                if (index == 0) {
                    // 기준점 이동
                    path.moveTo(point.x, point.y)
                } else {
                    // 이전 좌표와 입력된 좌표까지 직선을 추가한다. 입력된 좌표로 기준점 이동
                    path.lineTo(point.x, point.y)
                }
            }

            // 이어진 직선의 UI 커스텀
            drawPath(
                path = path,
                color = Primary500,
                style = Stroke(width = 4f)
            )

            // point indicator
            result.forEach { point ->
                drawCircle(
                    color = Primary500,
                    center = Offset(point.x, point.y),
                    radius = outCircle
                )

                drawCircle(
                    color = Color.White,
                    center = Offset(point.x, point.y),
                    radius = inCircle
                )
            }
        })
}

@Composable
private fun BoxScope.XAxis(
    maxWidth: IntSize,
    xAxisInfo: AxisInfo
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(start = (maxWidth.width).dp),
    ) {
        xAxisInfo.axisTick.forEachIndexed { i, value ->
            if (xAxisInfo.axisTick.lastIndex == i) {
                Text(
                    text = value.value.toString(),
                    style = caption2,
                    color = Gray400
                )
            } else {
                Text(
                    modifier = Modifier.weight(1f), text = value.value.toString(),
                    style = caption2,
                    color = Gray400
                )
            }
        }
    }
}

@Composable
private fun YAxis(
    axisInfo: AxisInfo,
    onSizeChangedText: (IntSize) -> Unit = {},
) {
    axisInfo.axisTick.asReversed().also {
        it.forEachIndexed { i, axis ->
            Row(
            ) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .align(Alignment.CenterVertically)
//                                .background(color = Color.Blue)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .onSizeChanged {
                                onSizeChangedText.invoke(it)
                            },
                        style = caption2,
                        maxLines = 1,
                        text = axis.value.toString(),
                        color = Gray400,
                        textAlign = TextAlign.End
                    )
                }
                GrayLine(
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .align(Alignment.CenterVertically),
                    height = if (i == it.lastIndex) 1.dp else 0.5.dp
                )
            }
        }
    }

}


data class Point(val x: Float, val y: Float)

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)
    androidx.compose.material3.Card(
        modifier = modifier
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

fun Float.calculatePercent(
    min: Float,
    max: Float,
): Float {
    return (this - min) / (max - min)
}


@Preview(showBackground = true, backgroundColor = 0x0000000)
@Composable
private fun PreviewLinearLineGraph() {
    val values = listOf(
        Point(3f, 6f),
        Point(6f, 5f),
        Point(9f, 3f),
        Point(12f, 10f),
    )
    val result = mutableListOf<Point>()
    values.forEach {
        val x = it.x.calculatePercent(min = 3f, max = 12f)
        val y = it.y.calculatePercent(min = 0f, max = 10f)
        result.add(Point(x, y))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//        .background(color = Color.DarkGray)
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "무릎 아래에 손가락이 5개 정도 들어가고, 무릎이 조금 구부러져 있어요",
                    style = body1,
                    fontSize = dpToSp(18.dp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                SimpleLineChartWithLabels(
                    values = result,
                    xAxisInfo = AxisInfo(
                        minValue = 0f,
                        maxValue = 10f,
                        axisTick = listOf(
                            AxisTick(0, "3주"),
                            AxisTick(1, "6주"),
                            AxisTick(2, "9주"),
                            AxisTick(3, "12주"),
                        )
                    ),
                    yAxisInfo = AxisInfo(
                        minValue = 0f,
                        maxValue = 10f,
                        axisTick = listOf(
                            AxisTick(0, "0"),
                            AxisTick(1, "5"),
                            AxisTick(2, "10"),
                        )
                    )
                )
            }

        }
    }
}
