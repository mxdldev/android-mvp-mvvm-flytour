package com.fly.tour.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Description: <软键盘的显示与隐藏><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 *
 *  * 1.显示软键盘
 *  * 2.隐藏软键盘
 *
</软键盘的显示与隐藏> */
object SoftInputUtil {

    /**
     * 显示软键盘
     *
     * @param context
     */
    fun showSoftInput(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager // 显示软键盘
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    fun showSoftInput(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager // 显示软键盘
        imm.showSoftInput(view, 0)
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    fun hideSoftInput(context: Context, view: View) {
        val immHide = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager // 隐藏软键盘
        immHide.hideSoftInputFromWindow(view.windowToken, 0)
    }
}