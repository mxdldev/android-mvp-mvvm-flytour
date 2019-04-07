package com.zjx.vcars.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import com.zjx.vcars.common.BaseApplication;

import static com.zjx.vcars.common.util.NetUtil.NetType.NET_4G;
import static com.zjx.vcars.common.util.NetUtil.NetType.NO_NET;
import static com.zjx.vcars.common.util.NetUtil.NetType.WIFI;


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
    public static NetType isNetWorkState(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.isConnected()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // Logger.v(TAG, "当前WiFi连接可用 ");
                    return WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // Logger.v(TAG, "当前移动网络连接可用 ");
                    return NET_4G;
                }
            } else {
                // Logger.v(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                return NO_NET;
            }
        } else {
            // Logger.v(TAG, "当前没有网络连接，请确保你已经打开网络 ");
            return NO_NET;
        }
        return NO_NET;
    }
    public enum NetType{WIFI,NET_4G,NO_NET};
}