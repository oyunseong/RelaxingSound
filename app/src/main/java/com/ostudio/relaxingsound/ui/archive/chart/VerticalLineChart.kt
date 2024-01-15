package com.ostudio.relaxingsound.ui.archive.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ostudio.relaxingsound.ui.theme.Gray200
import com.ostudio.relaxingsound.ui.theme.Gray400
import com.ostudio.relaxingsound.ui.theme.Primary500
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.axisLineComponent
import com.patrykandpatrick.vico.compose.axis.axisTickComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf

/**
 * vico 라이브러리를 이용한 막대 차트
 */
@Composable
fun VerticalLineChart(
    modifier: Modifier = Modifier,
    composedChartEntryModelProducer: ComposedChartEntryModelProducer,
    maxY: Float? = null,
) {
    val lineComponent1 = LineComponent(
        color = Primary500.toArgb(),
        thicknessDp = 16f,
        shape = shapeComponent(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        ).shape,
    )

    val lineComponent2 = LineComponent(
        color = Color(0xFF2DD4BF).toArgb(),
        thicknessDp = 16f,
        shape = shapeComponent(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        ).shape,
    )

    val axisValuesOverrider: AxisValuesOverrider<ChartEntryModel> =
        object : AxisValuesOverrider<ChartEntryModel> {
            override fun getMaxY(model: ChartEntryModel): Float? {
                return maxY
            }
        }

    val columnChart = columnChart(
        columns = listOf(lineComponent1, lineComponent2),
        innerSpacing = 4.dp,
        spacing = 37.dp,
        axisValuesOverrider = axisValuesOverrider,
    )

    val startAxis = rememberStartAxis(
        label = axisLabelComponent(
            color = Gray400,
            textSize = 15.sp
        ),
        axis = axisLineComponent(
            color = Color.Transparent,
            dynamicShader = null
        ),
        tick = axisTickComponent(
            thickness = 0.dp,
            color = Color.Red,
            brush = null
        ),
        guideline = axisGuidelineComponent(
            color = Gray200,
            thickness = 0.5.dp,
            shape = Shapes.rectShape,
        ),
        valueFormatter = { value, _ ->
            value.toInt().toString()
        },
        itemPlacer = remember { AxisItemPlacer.Vertical.default(maxItemCount = 3) },
    )

    val bottomAxis = rememberBottomAxis(
        label = axisLabelComponent(
            textSize = 15.sp,
            color = Gray400,
        ),
        axis = axisLineComponent(
            color = Gray200,
            thickness = 1.dp,
            dynamicShader = null,
        ),
        tick = axisTickComponent(
            color = Color.Transparent,
            brush = null,
        ),
        valueFormatter = { value, _ ->
            "${value.toInt()}주"
        },
        guideline = axisGuidelineComponent(
            color = Color.Transparent,
        ),
        itemPlacer = remember { AxisItemPlacer.Horizontal.default() },
    )


//    ProvideChartStyle(remember(columnChart) {
//        ChartStyle(
//            axis = ChartStyle.Axis(
//                axisGuidelineColor = Gray200,
//                axisLabelColor = Gray400,
//                axisLineColor = Gray200,
//            ),
//            columnChart = columnChart,
//            lineChart = lineComponent1,
//            marker =,
//            elevationOverlayColor =
//        )
//    }) {
//
//    }

    Chart(
        modifier = modifier.height(100.dp),
        chart = remember(columnChart) { columnChart },
        chartModelProducer = composedChartEntryModelProducer,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
    )
//    Chart(
//        modifier = modifier.height(100.dp),
//        chart = remember(columnChart) { columnChart },
//        chartModelProducer = composedChartEntryModelProducer,
//        startAxis = startAxis,
//        bottomAxis = bottomAxis,
//    )
}

@Preview
@Composable
private fun PreviewVicoChart() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        VerticalLineChart(
            composedChartEntryModelProducer = ComposedChartEntryModelProducer.build {
                add(entriesOf(8, 20, 20, 22))
                add(entriesOf(20, 20, 22, 24))
            },
            maxY = 30f
        )
    }
}