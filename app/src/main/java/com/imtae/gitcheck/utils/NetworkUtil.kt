package com.imtae.gitcheck.utils

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
class NetworkUtil(private val context: Context) {

    fun networkInfo(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: null

        return networkInfo != null && networkInfo.isConnected
    }
}