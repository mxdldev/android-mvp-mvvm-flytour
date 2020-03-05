package com.fly.tour.common.mvp

import android.content.Context

import com.trello.rxlifecycle2.LifecycleProvider

/**
 * Description: <BaseModel><br>
 * Author:      mxdl<br>
 * Date:        2018/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseModel> */
open class BaseModel(val context: Context) {
    var lifecycle: LifecycleProvider<*>? = null
        private set

    fun injectLifecycle(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = lifecycle
    }

    fun destory() {}
}
