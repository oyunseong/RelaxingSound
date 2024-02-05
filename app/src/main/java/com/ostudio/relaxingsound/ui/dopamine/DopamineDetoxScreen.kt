package com.ostudio.relaxingsound.ui.dopamine

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar


@Composable
fun DopamineDetoxScreen() {
    Content()
}

@Composable
fun Content() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (context.isUsageStatsPermission()) {
            /**
             * getPackageName : 앱이름
             * getLastTimeUsed : 마지막으로 사용한 시간
             * getTotalTimeInForeground : Foreground에서 실행된 전체 시간
             * getAppLaunchCount : 실행된 횟수
             */
            val appUsageStats = context.getUsageStats()

            AppUsageStatsList(appUsageStats)
            for (usageStats in appUsageStats) {
                Log.d(
                    "++##",
                    "${usageStats.packageName}: ${usageStats.totalTimeInForeground} milliseconds"
                )
            }
        }
    }
}

@Composable
fun AppUsageStatsList(items: List<UsageStats>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(items.size) {
                Text(text = "${items[it].packageName}: ${items[it].totalTimeInForeground} milliseconds")

            }
        })
}

private fun Context.getUsageStats(): List<UsageStats> {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -1) // Get usage stats from the last 24 hours
    calendar.add(Calendar.YEAR, -1) // Get usage stats from the last 24 hours

    val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    return usageStatsManager.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        calendar.timeInMillis,
        System.currentTimeMillis()
    )
}

//https://github.com/googlesamples/android-AppUsageStatistics


