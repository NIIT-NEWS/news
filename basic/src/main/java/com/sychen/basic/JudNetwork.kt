package com.sychen.basic

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager

/**Judging the network environment
 * 判断网络环境
 */
class JudNetwork {
    companion object {
        fun JudNetwork(context: Context): Boolean {
            val manager: ConnectivityManager =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkinfo = manager.activeNetworkInfo
            if (networkinfo == null ||!networkinfo.isAvailable) {
                return false
            }
            return true
        }
    }

    /**
     * (i & 0xFF ) + "." +
    ((i >> 8 ) & 0xFF) + "." +
    ((i >> 16 ) & 0xFF) + "." +
    ( i >> 24 & 0xFF)
     */
}