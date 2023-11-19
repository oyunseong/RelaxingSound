package com.ostudio.relaxingsound.ui.archive.graph

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import com.ostudio.relaxingsound.ui.theme.Gray400
import com.ostudio.relaxingsound.ui.theme.Primary500
import com.ostudio.relaxingsound.ui.theme.body1
import com.ostudio.relaxingsound.ui.theme.caption2
import com.ostudio.relaxingsound.ui.theme.dpToPx
import com.ostudio.relaxingsound.ui.theme.dpToSp

data class AxisInfo(
    val minValue: Float,
    val maxValue: Float,
    val axisLabel: List<AxisTick>,
)

data class AxisTick(val position: Int, val value: String)

@Composable
fun SimpleChartWithLabels(
    modifier: Modifier = Modifier,
    height: Int = 89,
    xAxisInfo: AxisInfo,
    yAxisInfo: AxisInfo,
    maxWidth: Dp = 10.dp,
    onSizeChangedText: (IntSize) -> Unit = {},
    content: @Composable () -> Unit = {},
) {

    var yAxisLabelHeight by remember { mutableStateOf(0f) }
    val n = yAxisInfo.axisLabel.size
    var yLabelVerticalSpace = 0f
    LaunchedEffect(key1 = yAxisLabelHeight, block = {
        yLabelVerticalSpace = (height - (n * yAxisLabelHeight)) / n   // 함수로 분리 + 네이밍
    })


    Log.d("++##", "maxWidth ${maxWidth}")
    Log.d("++##", "yLabelVerticalSpace ${yLabelVerticalSpace}")
    Log.d("++##", "n ${n}")
    Log.d("++##", "height ${height}")
    Log.d("++##", "yAxisLabelHeight ${yAxisLabelHeight}")

    Column(
        modifier = modifier
            .height(height.dp)  // 입력으로 높이를 고정
            .fillMaxWidth()
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 13.dp),
                verticalArrangement = Arrangement.spacedBy(yLabelVerticalSpace.dp)
            ) {
                YAxis(
                    axisInfo = yAxisInfo,
//                    labelWidth = 10.dp,
                    onSizeChangedText = {
                        if (maxWidth < it.width.dp) {
                            onSizeChangedText.invoke(it)
                            Log.d("++##", "call $it")
                            Log.d("++##", "maxWidth $maxWidth")
                        }
                    },
                    textHeight = {
                        yAxisLabelHeight = 15f
                    }
                )
            }

            XAxis(
                maxWidth = maxWidth,
                xAxisInfo = xAxisInfo
            )

            content.invoke()
        }
    }
}


@Composable
fun SimpleLineChart(
    modifier: Modifier,
    points: List<Point>,
) {
    val outCircle = dpToPx(dp = 4f)
    val inCircle = dpToPx(dp = 2f)

    Box(modifier = modifier
        .padding(top = 8.dp) // y축의
//        .background(color = Color(0x50FFFF00))
        .drawBehind {
            /**
             * 설정된 비율에 맞게 해당 x,y값을 px로 변환
             */
            val relativeCoordinatePoints = points.map {
                it.copy(
                    x = it.x * size.width,
                    y = size.height - it.y * size.height
                )
            }

            val path = Path()
            relativeCoordinatePoints.forEachIndexed { index, point ->
                if (index == 0) {
                    // 기준점 이동
                    path.moveTo(point.x, point.y)
                } else {
                    // 이전 좌표와 입력된 좌표까지 직선을 추가한다. 입력된 좌표로 기준점 이동
                    path.lineTo(point.x, point.y)
                }
            }

            // 이어진 직선의 UI
            drawPath(
                path = path,
                color = Primary500,
                style = Stroke(width = 4f)
            )

            // 좌표 indicator
            relativeCoordinatePoints.forEach { point ->
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
    maxWidth: Dp,
    xAxisInfo: AxisInfo
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomStart)
    ) {
        Spacer(modifier = Modifier.width(maxWidth))
        xAxisInfo.axisLabel.forEachIndexed { i, it ->
            if (xAxisInfo.axisLabel.lastIndex == i) {
                Text(
                    text = it.value,
                    style = caption2,
                    color = Gray400
                )
            } else {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.Blue),
                    text = it.value,
                    style = caption2,
                    color = Gray400
                )
            }
        }
    }
}

/**
 * Y축 라벨과 회색 가로선
 */
@Composable
private fun YAxis(
    axisInfo: AxisInfo,
    labelWidth: Dp = 10.dp, // TODO  현재 고정값 가변으로 변경 필요
    onSizeChangedText: (IntSize) -> Unit = {},
    textHeight: (Float) -> Unit = {}
) {
    textHeight.invoke(caption2.lineHeight.value)
    Log.d("++##", "YAxis : $labelWidth")

    axisInfo.axisLabel.asReversed().also {
        it.forEachIndexed { i, axis ->
            Row {
                Box(
                    modifier = Modifier
                        .width(labelWidth)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        modifier = Modifier
                            .height(15.dp)
                            .align(Alignment.CenterEnd)
                            .onSizeChanged { intSize ->
                                onSizeChangedText.invoke(intSize)
                            },
                        style = caption2,
                        maxLines = 1,
                        text = axis.value,
                        color = Gray400,
                        textAlign = TextAlign.End
                    )
                }
                GrayLine(
                    modifier = Modifier
                        .padding(start = 18.dp, end = 9.dp)
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

    val avgValues = listOf(
        Point(3f, 8f),
        Point(6f, 2f),
        Point(9f, 7f),
        Point(12f, 4f),
    )

    val xAxisInfo = AxisInfo(
        minValue = 0f,
        maxValue = 10f,
        axisLabel = listOf(
            AxisTick(0, "3주"),
            AxisTick(1, "6주"),
            AxisTick(2, "9주"),
            AxisTick(3, "12주"),
        )
    )
    val yAxisInfo = AxisInfo(
        minValue = 0f,
        maxValue = 10f,
        axisLabel = listOf(
            AxisTick(0, "0"),
            AxisTick(1, "5"),
            AxisTick(2, "10"),
        )
    )

    val result = mutableListOf<Point>()
    val avgResult = mutableListOf<Point>()

    values.forEach {
        val x = it.x.calculatePercent(min = 3f, max = 12f)
        val y = it.y.calculatePercent(min = 0f, max = 10f)
        result.add(Point(x, y))
    }

    avgValues.forEach {
        val x = it.x.calculatePercent(min = 3f, max = 12f)
        val y = it.y.calculatePercent(min = 0f, max = 10f)
        avgResult.add(Point(x, y))
    }

    var maxWidth by remember { mutableStateOf(0.dp) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "무릎 아래에 손가락이 5개 정도 들어가고, 무릎이 조금 구부러져 있어요",
                    style = body1,
                    fontSize = dpToSp(18.dp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                SimpleChartWithLabels(
                    xAxisInfo = xAxisInfo,
                    yAxisInfo = yAxisInfo,
//                    maxWidth = maxWidth,
                    onSizeChangedText = {
                        maxWidth = it.width.dp
                    },
                    content = {
                        SimpleLineChart(
                            points = result,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 29.dp, bottom = 21.dp, end = 10.dp)
                        )

                        SimpleLineChart(
                            points = avgResult,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 29.dp, bottom = 21.dp, end = 10.dp)
                        )
                    }
                )
            }

        }
    }
}
