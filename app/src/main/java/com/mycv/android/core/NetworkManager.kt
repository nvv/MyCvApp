package com.mycv.android.core

import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkManager(private val manager: ConnectivityManager) {

    fun isNetworkAvailable(): Boolean {
        val activeNetworkInfo: NetworkInfo? = manager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }

}