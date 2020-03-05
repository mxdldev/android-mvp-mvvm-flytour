package com.fly.tour.common.mvp

/**
 * Description: <BaseRefreshView><br>
 * Author:      mxdl<br>
 * Date:        2018/2/26<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseRefreshView> */
interface BaseRefreshView<T> : BaseRefreshContract.View {
    //刷新数据
    fun refreshData(data: List<T>)

    //加载更多
    fun loadMoreData(data: List<T>)
}
