package com.fly.tour.common.mvp

/**
 * Description: <基本的刷新数据协议><br>
 * Author:      mxdl<br>
 * Date:        2018/2/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</基本的刷新数据协议> */
interface BaseRefreshContract {
    interface Presenter {
        /**
         * 刷新数据
         */
        fun refreshData()

        /**
         * 加载更多
         */
        fun loadMoreData()
    }

    interface View : BaseView {
        /**
         * 是否启用下拉刷新
         * @param b
         */
        fun enableRefresh(b: Boolean)

        /**
         * 是否启用上拉加载更多
         */
        fun enableLoadMore(b: Boolean)

        /**
         * 刷新回调
         */
        fun onRefreshEvent()

        /**
         * 加载更多的回调
         */
        fun onLoadMoreEvent()

        /**
         * 自动加载的事件
         */
        fun onAutoLoadEvent()

        /**
         * 停止刷新
         */
        fun stopRefresh()

        /**
         * 停止加载更多
         */
        fun stopLoadMore()

        /**
         * 自动加载数据
         */
        fun autoLoadData()
    }
}
