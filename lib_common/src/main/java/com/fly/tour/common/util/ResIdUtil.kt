package com.fly.tour.common.util

/**
 * Description: <动态获取资源id><br>
 * Author: mxdl<br>
 * Date: 2018/6/19<br>
 * Version: V1.0.0<br>
 * Update: <br>
</动态获取资源id> */
import android.content.Context

object ResIdUtil {


    /**
     * 获取id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun id(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "id", context.packageName)
    }

    /**
     * 获取anim类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun anim(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "anim", context.packageName)
    }

    /**
     * 获取layout类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun layout(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "layout", context.packageName)
    }

    /**
     * 获取drawable类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun drawable(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "drawable", context.packageName)
    }

    /**
     * 获取string类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun string(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "string", context.packageName)
    }

    /**
     * 获取raw类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun raw(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "raw", context.packageName)
    }

    /**
     * 获取style类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    fun style(context: Context, resName: String): Int {
        return context.resources.getIdentifier(resName, "style", context.packageName)
    }
}