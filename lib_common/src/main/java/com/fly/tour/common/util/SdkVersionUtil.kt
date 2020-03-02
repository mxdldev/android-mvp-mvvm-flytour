package com.fly.tour.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build


/**
 * 版本相关
 */
object SdkVersionUtil {

    /**
     * hasForyo
     *
     * @return true false
     */
    fun hasFroyo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
    }

    /**
     * hasGingerbread
     *
     * @return true false
     */
    fun hasGingerbread(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
    }

    /**
     * hasHoneycomb
     *
     * @return true false
     */
    fun hasHoneycomb(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
    }

    /**
     * hasHoneycombMR1
     *
     * @return true false
     */
    fun hasHoneycombMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
    }

    /**
     * hasHoneycombMR2
     *
     * @return true false
     */
    fun hasHoneycombMR2(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2
    }

    /**
     * hasIceCreamSandwich
     *
     * @return true false
     */
    fun hasIceCreamSandwich(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
    }

    /**
     * hasJellyBean
     *
     * @return true false
     */
    fun hasJellyBean(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    /**
     * 4.2以上
     *
     * @return true false
     */
    fun aboveJellyBean(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN
    }

    fun getAppVersion(context: Context): Int {
        var version = 0
        try {
            version = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }
}