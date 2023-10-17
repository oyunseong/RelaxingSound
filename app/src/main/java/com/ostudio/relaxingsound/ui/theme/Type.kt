package com.ostudio.relaxingsound.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ostudio.relaxingsound.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun pxToDp(pixels: Float) = with(LocalDensity.current) { pixels.toDp() }

@Composable
fun dpToPx(dp: Float): Float {
    return with(LocalDensity.current) {
        dp * density
    }
}

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
)

val body1: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(13.dp),
        lineHeight = dpToSp(27.dp),
        letterSpacing = dpToSp((-0.2).dp)
    )

val body2: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(16.dp),
        lineHeight = dpToSp(24.dp),
        letterSpacing = dpToSp((-0.3).dp)
    )

val body3: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(14.dp),
        lineHeight = dpToSp(21.dp),
        letterSpacing = dpToSp((-0.3).dp)
    )

val subhead1: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(28.dp),
        lineHeight = dpToSp(42.dp),
        letterSpacing = dpToSp((-0.3).dp)
    )

val subhead2: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(24.dp),
        lineHeight = dpToSp(36.dp),
        letterSpacing = dpToSp((-0.3).dp)
    )

val subhead2Medium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(24.dp),
        lineHeight = dpToSp(36.dp),
        letterSpacing = dpToSp((-0.3).dp),
        fontWeight = FontWeight.Medium
    )

val subhead3: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(20.dp),
        lineHeight = dpToSp(30.dp),
        letterSpacing = dpToSp((-0.3).dp)
    )

val heading1: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(40.dp),
        lineHeight = dpToSp(46.dp),
        letterSpacing = dpToSp((-0.5).dp)
    )

val heading2: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(36.dp),
        lineHeight = dpToSp(46.dp),
        letterSpacing = dpToSp((-0.5).dp)
    )

val heading3: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(32.dp),
        lineHeight = dpToSp(48.dp),
        letterSpacing = dpToSp((-0.3).dp)
    )

val caption1: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(12.dp),
        lineHeight = dpToSp(18.dp),
        letterSpacing = dpToSp((-0.2).dp),
        fontWeight = FontWeight(500)
    )

val caption2: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(10.dp),
        lineHeight = dpToSp(15.dp),
        letterSpacing = dpToSp((-0.2).dp),
        fontWeight = FontWeight(400)
    )

val painLevel: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(60.dp),
        lineHeight = dpToSp(71.6.dp),
        letterSpacing = dpToSp((-0.5).dp),
        fontWeight = FontWeight.Bold
    )

val countDown: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(100.dp),
        lineHeight = dpToSp(100.dp),
        fontWeight = FontWeight.Bold,
        letterSpacing = dpToSp((-0.2).dp),
        shadow = Shadow(
            color = Color.Black,
            blurRadius = 25f
        )
    )

val readyPose: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(70.dp),
        lineHeight = dpToSp(70.dp),
        fontWeight = FontWeight.Bold,
        letterSpacing = dpToSp((-0.2).dp),
        shadow = Shadow(
            color = Color.Black,
            blurRadius = 25f
        )
    )

val TypoAc = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val snackbarFontStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontSize = dpToSp(16.dp),
        lineHeight = dpToSp(24.dp),
        letterSpacing = dpToSp((-0.2).dp)
    )