package com.fly.tour.common.mvp

import android.content.Context

import com.trello.rxlifecycle2.LifecycleProvider


/**
 * Description: <BasePresenter><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BasePresenter> */
abstract class BasePresenter<M : BaseModel, V>(protected var mContext: Context) {
    protected var mView: V? = null
    protected var mModel: M? = null

    fun attach(view: V) {
        attachView(view)
        attachModel()
    }

    fun detach() {
        detachView()
        detachModel()
    }

    fun attachView(view: V) {
        mView = view
    }

    fun detachView() {
        mView = null
    }

    fun attachModel() {
        mModel = initModel()
    }

    fun detachModel() {
        mModel?.destory()
        mModel = null
    }

    abstract fun initModel(): M

    fun injectLifecycle(lifecycle: LifecycleProvider<*>) {
        mModel?.injectLifecycle(lifecycle)
    }
}
