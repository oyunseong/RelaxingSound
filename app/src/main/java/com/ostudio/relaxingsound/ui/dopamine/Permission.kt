package com.ostudio.relaxingsound.ui.dopamine

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings


/**
 * android:get_usage_stats 권한을 요청합니다.
 * AppOpsManager는 런타임 권한 액세스 제어 및 추적 지원부터 배터리 소모량 추적에 이르기까지 다양한 기능을 지원합니다.
 */
fun Context.isUsageStatsPermission(): Boolean {
    val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val mode = appOpsManager.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val mode = appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    return false
}


/**
 * 사용정보 접근 허용 설정 화면으로 이동합니다.
 * 설정 화면으로 이동하고 돌아오는 동작을 하기 때문에
 * onActivityResult resultCode는 항상 RESULT_CANCELED 입니다.
 */
fun Activity.requestUsageStatsPermission(requestCode: Int = 1291) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    startActivityForResult(intent, requestCode)
}