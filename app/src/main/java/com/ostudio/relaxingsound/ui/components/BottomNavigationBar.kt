package com.ostudio.relaxingsound.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ostudio.relaxingsound.ui.navigation.model.BottomNavItem

val bottomBarHeight = 60.dp

val screens = listOf(
    BottomNavItem.Home,
    BottomNavItem.Alarm,
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    isBottomBar: (Boolean) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any {
        it.screen.name == currentDestination?.route //&&
//                it.screen.name != BottomNavItem.Sale.screen.name &&
//                it.screen.name != BottomNavItem.Purchase.screen.name
    }
    isBottomBar.invoke(bottomBarDestination)

    if (bottomBarDestination) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomBarHeight),
            backgroundColor = Color.White
        ) {
            screens.forEach { screen ->
                AddItem(
                    item = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == item.screen.name } == true
    BottomNavigationItem(
        icon = {
            Icon(
                painter = painterResource(id = item.icon),
                tint = if (selected) Color.Yellow else Color.Unspecified,
                contentDescription = "Navigation Icon"
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(item.screen.name) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}