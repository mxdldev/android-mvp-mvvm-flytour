package com.refresh.lib

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Description: <BaseRefreshLayout><br>
 * Author:      mxdl<br>
 * Date:        2019/2/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseRefreshLayout> */
abstract class BaseRefreshLayout(context: Context, attrs: AttributeSet) :
    SuperSwipeRefreshLayout(context, attrs) {
    private var isEnableRefresh = true//是否启用下拉刷新
    private var isEnableLoadMore = true//是否启用上拉加载更多
    protected var mOnRefreshListener: OnRefreshListener? = null//下拉刷新监听器
    protected var mOnLoadMoreListener: OnLoadMoreListener? = null//上拉加载更多监听器
    protected var mOnAutoLoadListener: OnAutoLoadListener? = null//自动加载的回调

    interface OnRefreshListener {
        fun onRefresh()
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    //调用autoLoad的回调
    interface OnAutoLoadListener {
        fun onAutoLoad()
    }

    fun setOnRefreshListener(onRefreshListener: OnRefreshListener) {
        mOnRefreshListener = onRefreshListener
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener
    }

    fun setOnAutoLoadListener(onAutoLoadListener: OnAutoLoadListener) {
        mOnAutoLoadListener = onAutoLoadListener
    }

    /**
     * 是否启用下拉刷新
     * @param enableRefresh
     */
    fun setEnableRefresh(enableRefresh: Boolean) {
        isEnableRefresh = enableRefresh
    }

    /**
     * 是否启用加载更多
     * @param enableLoadMore
     */
    fun setEnableLoadMore(enableLoadMore: Boolean) {
        isEnableLoadMore = enableLoadMore
    }

    /**
     * 自动刷新
     */
    fun autoRefresh() {
        postDelayed({
            showRefresh()
            isRefreshing = true
            mOnAutoLoadListener?.onAutoLoad()
        }, (1000 * 1).toLong())
    }

    /**
     * 如果禁用了加载更多则就直接返回了
     * @param ev
     * @param action
     * @return
     */
    override fun handlerPushTouchEvent(ev: MotionEvent, action: Int): Boolean {
        return if (!isEnableLoadMore) {
            false
        } else super.handlerPushTouchEvent(ev, action)
    }

    /**
     * 如果禁用了就直接返回了
     * @param ev
     * @param action
     * @return
     */
    override fun handlerPullTouchEvent(ev: MotionEvent, action: Int): Boolean {
        return if (!isEnableRefresh) {
            false
        } else super.handlerPullTouchEvent(ev, action)
    }

    abstract fun showRefresh()
}
