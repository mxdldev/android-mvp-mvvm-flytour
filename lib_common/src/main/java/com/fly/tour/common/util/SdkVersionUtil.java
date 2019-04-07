package com.zjx.vcars.common.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;


/**
 * 版本相关
 */
public class SdkVersionUtil {

    /**
     * hasForyo
     *
     * @return true false
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * hasGingerbread
     *
     * @return true false
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * hasHoneycomb
     *
     * @return true false
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * hasHoneycombMR1
     *
     * @return true false
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * hasHoneycombMR2
     *
     * @return true false
     */
    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * hasIceCreamSandwich
     *
     * @return true false
     */
    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * hasJellyBean
     *
     * @return true false
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 4.2以上
     *
     * @return true false
     */
    public static boolean aboveJellyBean() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN;
    }

    public static int getAppVersion(Context context) {
        int version = 0;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}