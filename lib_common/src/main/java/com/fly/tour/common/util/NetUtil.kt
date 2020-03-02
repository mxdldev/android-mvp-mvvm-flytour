package com.fly.tour.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.fly.tour.common.BaseApplication

import com.fly.tour.common.util.NetUtil.NetType.NET_4G
import com.fly.tour.common.util.NetUtil.NetType.NO_NET
import com.fly.tour.common.util.NetUtil.NetType.WIFI


/**
 * Description: <ToastUtil><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</ToastUtil> */
object NetUtil {

    fun checkNet(): Boolean {
        val context = BaseApplication.instance
        return isWifiConnection(context!!) || isStationConnection(context!!)
    }

    fun checkNetToast(): Boolean {
        val isNet = checkNet()
        if (!isNet) {
            ToastUtil.showToast("网络不给力哦！")
        }
        return isNet
    }

    /**
     * 是否使用基站联网
     *
     * @param context
     * @return
     */
    fun isStationConnection(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return if (networkInfo != null) {
            networkInfo.isAvailable && networkInfo.isConnected
        } else false
    }

    /**
     * 是否使用WIFI联网
     *
     * @param context
     * @return
     */
    fun isWifiConnection(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if (networkInfo != null) {
            networkInfo.isAvailable && networkInfo.isConnected
        } else false
    }

    fun isNetWorkState(context: Context): NetType {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.isConnected) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    // Logger.v(TAG, "当前WiFi连接可用 ");
                    return WIFI
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    // Logger.v(TAG, "当前移动网络连接可用 ");
                    return NET_4G
                }
            } else {
                // Logger.v(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                return NO_NET
            }
        } else {
            // Logger.v(TAG, "当前没有网络连接，请确保你已经打开网络 ");
            return NO_NET
        }
        return NO_NET
    }

    enum class NetType {
        WIFI, NET_4G, NO_NET
    }
}