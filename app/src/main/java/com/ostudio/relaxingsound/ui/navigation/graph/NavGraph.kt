package com.ostudio.relaxingsound.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ostudio.relaxingsound.ui.alarm.AlarmScreen
import com.ostudio.relaxingsound.ui.home.HomeScreen
import com.ostudio.relaxingsound.ui.navigation.model.NavScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = NavScreen.SPLASH.name,
        startDestination = NavScreen.HOME.name
    ) {
        composable(route = NavScreen.HOME.name){
            HomeScreen()
        }

        composable(route = NavScreen.ALARM.name){
            AlarmScreen()
        }

    }
}