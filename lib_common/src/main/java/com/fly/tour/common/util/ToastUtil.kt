package com.fly.tour.common.util

import android.widget.Toast

import com.fly.tour.common.BaseApplication

/**
 * Description: <吐司工具类><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</吐司工具类> */
object ToastUtil {

    fun showToast(message: String) {
        Toast.makeText(BaseApplication.instance, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(resid: Int) {
        Toast.makeText(BaseApplication.instance, BaseApplication.instance?.getString(resid), Toast.LENGTH_SHORT)
                .show()
    }
}