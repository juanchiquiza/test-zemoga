package com.example.zemoga.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectionManager {

    fun validateConnectionWifi(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return wifiInfo?.isConnected ?: false
    }
}