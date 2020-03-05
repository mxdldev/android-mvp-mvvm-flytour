package com.fly.tour.common.base

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View

import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.common.mvp.BaseRefreshPresenter
import com.fly.tour.common.mvp.BaseRefreshView
import com.fly.tour.common.util.log.KLog
import com.refresh.lib.BaseRefreshLayout
import com.refresh.lib.DaisyRefreshLayout

/**
 * Description: <下拉刷新></下拉刷新>、上拉加载更多的Fragment><br>
 * Author:      mxdl<br>
 * Date:        2018/2/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
abstract class BaseRefreshFragment<M : BaseModel, V : BaseRefreshView<T>, P : BaseRefreshPresenter<M, V, T>, T> :
    BaseMvpFragment<M, V, P>(), BaseRefreshView<T> {
    protected var mRefreshLayout: DaisyRefreshLayout? = null

    override fun initCommonView(view: View) {
        super.initCommonView(view)
        initRefreshView(view)
    }

    fun initRefreshView(view: View) {
        mRefreshLayout = view.findViewById(onBindRreshLayout())
        mRefreshLayout?.setOnRefreshListener(object : BaseRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                onRefreshEvent()
            }
        })
        mRefreshLayout?.setOnLoadMoreListener(object : BaseRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                onLoadMoreEvent()
            }
        })
        mRefreshLayout?.setOnAutoLoadListener(object : BaseRefreshLayout.OnAutoLoadListener {
            override fun onAutoLoad() {
                onAutoLoadEvent()
            }
        })
    }

    protected abstract fun onBindRreshLayout(): Int
    override fun enableRefresh(b: Boolean) {
        mRefreshLayout?.setEnableRefresh(b)
    }

    override fun enableLoadMore(b: Boolean) {
        mRefreshLayout?.setEnableLoadMore(b)
    }

    override fun stopRefresh() {
        mRefreshLayout?.isRefreshing = false
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun stopLoadMore() {
        mRefreshLayout?.setLoadMore(false)
    }

    override fun autoLoadData() {
        KLog.v("MYTAG", "autoLoadData1 start...")
        mRefreshLayout?.autoRefresh()
    }
}
