package com.fly.tour.common.base

import android.os.Bundle

import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.common.mvp.BasePresenter

/**
 * Description: <BaseMvpActivity><br>
 * Author:      mxdl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
abstract class BaseMvpActivity<M : BaseModel, V, P : BasePresenter<M, V>> : BaseActivity() {
    protected var mPresenter: P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter?.attach(this as V)
        mPresenter?.injectLifecycle(this)
        super.onCreate(savedInstanceState)

    }

    abstract fun initPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
    }
}
