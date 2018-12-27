package com.fly.tour.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.fly.tour.common.BaseApplication;

/**
 * Description: <ToastUtil><br>
 * Author: gxl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class NetUtil {

    public static boolean checkNet() {
        Context context = BaseApplication.getInstance();
        return isWifiConnection(context) || isStationConnection(context);
    }

    public static boolean checkNetToast() {
        boolean isNet = checkNet();
        if (!isNet) {
            ToastUtil.showToast("网络不给力哦！");
        }
        return isNet;
    }

    /**
     * 是否使用基站联网
     *
     * @param context
     * @return
     */
    public static boolean isStationConnection(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isAvailable() && networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 是否使用WIFI联网
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnection(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isAvailable() && networkInfo.isConnected();
        }
        return false;
    }

}