package com.fly.tour.common.mvvm.view;

import java.util.List;

/**
 * Description: <IBaseRefreshView><br>
 * Author:      mxdl<br>
 * Date:        2019/6/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface IBaseRefreshView<T> extends IBaseView{
    //刷新数据
    void refreshData(List<T> data);
    //加载更多
    void loadMoreData(List<T> data);

    /**
     * 是否启用下拉刷新
     * @param b
     */
    void enableRefresh(boolean b);

    /**
     *是否启用上拉加载更多
     */
    void enableLoadMore(boolean b);

    /**
     *刷新回调
     */
    void onRefreshEvent();

    /**
     * 加载更多的回调
     */
    void onLoadMoreEvent();

    /**
     * 自动加载的事件
     */
    void onAutoLoadEvent();

    /**
     * 停止刷新
     */
    void stopRefresh();

    /**
     * 停止加载更多
     */
    void stopLoadMore();

    /**
     * 自动加载数据
     */
    void autoLoadData();
}
