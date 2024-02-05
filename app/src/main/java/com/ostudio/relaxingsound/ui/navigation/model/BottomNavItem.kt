package com.ostudio.relaxingsound.ui.navigation.model

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val screen: NavScreen
) {
    object Home : BottomNavItem(
        title = "홈",
        icon = androidx.core.R.drawable.ic_call_answer,
        screen = NavScreen.HOME
    )

    object Alarm : BottomNavItem(
        title = "알람",
        icon = androidx.core.R.drawable.ic_call_answer,
        screen = NavScreen.ALARM
    )

    object Test : BottomNavItem(
        title = "테스트",
        icon = androidx.core.R.drawable.ic_call_answer,
        screen = NavScreen.TEST
    )

    object Dopamine : BottomNavItem(
        title = "도파민",
        icon = androidx.core.R.drawable.ic_call_answer_video,
        screen = NavScreen.DOPAMINE
    )
}