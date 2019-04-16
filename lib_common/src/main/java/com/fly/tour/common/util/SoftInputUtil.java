package com.fly.tour.common.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Description: <软键盘的显示与隐藏><br>
 * Author: gxl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 * <ul>
 * <li>1.显示软键盘</li>
 * <li>2.隐藏软键盘</li>
 * </ul>
 */
public class SoftInputUtil {

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 显示软键盘
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 显示软键盘
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager immHide =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 隐藏软键盘
        immHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}