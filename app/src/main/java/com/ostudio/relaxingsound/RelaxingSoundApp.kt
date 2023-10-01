package com.ostudio.relaxingsound

import android.annotation.SuppressLint
import android.content.IntentSender.OnFinished
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ostudio.relaxingsound.extensions.showToast
import com.ostudio.relaxingsound.ui.components.BottomNavigationBar
import com.ostudio.relaxingsound.ui.components.bottomBarHeight
import com.ostudio.relaxingsound.ui.navigation.graph.NavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelaxingSoundApp(onFinished: () -> Unit = {}) {
    val navController = rememberNavController()
    var bottomPadding = 0.dp
    val context = LocalContext.current

    var start: Long = 0
    var end: Long = 0

    BackHandler {
        if (end > System.currentTimeMillis()) {
            onFinished.invoke()
        } else {
            start = System.currentTimeMillis()
            end = start.plus(2000)
            // show dialog
            context.showToast("한번 더 누르면 종료")
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController) {
                bottomPadding = if (it) bottomBarHeight else 0.dp
            }
        }
    ) { _ ->
        NavGraph(
            modifier = Modifier.padding(bottom = bottomPadding),
            navController = navController,
        )
    }
}

@Preview
@Composable
private fun PreviewRelaxingSoundApp() {
    RelaxingSoundApp()
}