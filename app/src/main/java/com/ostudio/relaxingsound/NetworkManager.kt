package com.ostudio.relaxingsound

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NetworkManager {
    private val _isNetworkAvailable = MutableStateFlow(false)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isNetworkAvailable.value = true
        }

        override fun onLost(network: Network) {
            _isNetworkAvailable.value = false
        }
    }

    fun initialize(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        // 초기 네트워크 상태 확인
        _isNetworkAvailable.value = checkNetworkStatus(connectivityManager)
    }

    /***
     * 네트워크 콜백 해제
     */
    fun release(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    /**
     * 네트워크 상태를 체크하는 함수
     *
     * 1. 현재 네트워크 상태 확인
     * 2. 활성화된 네트워크의 기능을 확인
     * 3. 네트워크가 인터넷 연결 기능을 가지고 있는지 확인하고 그 결과를 반환
     */
    private fun checkNetworkStatus(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
//suspend fun observeNetworkState(
//    onAvailable: () -> Unit = {},
//    onUnavailable: () -> Unit = {}
//) {
//    NetworkManager.isNetworkAvailable.collect { isAvailable ->
//        if (isAvailable) {
//            onAvailable.invoke()
//        } else {
//            onUnavailable.invoke()
//        }
//    }
//}
