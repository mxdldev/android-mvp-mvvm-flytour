package com.fly.tour.common.util;

/**
 * Description: <动态获取资源id><br>
 * Author: gxl<br>
 * Date: 2018/6/19<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
import android.content.Context;

public class ResIdUtil {


    /**
     * 获取id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int id(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "id", context.getPackageName());
    }

    /**
     * 获取anim类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int anim(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "anim", context.getPackageName());
    }

    /**
     * 获取layout类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int layout(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "layout", context.getPackageName());
    }

    /**
     * 获取drawable类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int drawable(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }

    /**
     * 获取string类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int string(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "string", context.getPackageName());
    }

    /**
     * 获取raw类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int raw(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "raw", context.getPackageName());
    }

    /**
     * 获取style类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int style(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style", context.getPackageName());
    }
}