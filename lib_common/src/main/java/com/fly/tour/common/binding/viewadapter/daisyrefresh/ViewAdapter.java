package com.fly.tour.common.binding.viewadapter.daisyrefresh;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.fly.tour.common.binding.command.BindingCommand;
import com.refresh.lib.BaseRefreshLayout;
import com.refresh.lib.DaisyRefreshLayout;

/**
 * Description: <ViewAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/7/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class ViewAdapter {
    //下拉刷新命令
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(DaisyRefreshLayout refreshLayout, final BindingCommand onRefreshCommand) {
        refreshLayout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }
    //上拉加载更多命令
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(DaisyRefreshLayout refreshLayout, final BindingCommand onLoadMoreCommond) {
        refreshLayout.setOnLoadMoreListener(new BaseRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (onLoadMoreCommond != null) {
                    onLoadMoreCommond.execute();
                }
            }
        });
    }

    //上拉加载更多命令
    @BindingAdapter({"onAutoRefreshCommand"})
    public static void onAutoRefreshCommand(DaisyRefreshLayout refreshLayout, final BindingCommand onAutoRerefeshCommond) {
        refreshLayout.setOnAutoLoadListener(new BaseRefreshLayout.OnAutoLoadListener() {
            @Override
            public void onAutoLoad() {
                if (onAutoRerefeshCommond != null) {
                    onAutoRerefeshCommond.execute();
                }
            }
        });

    }

    //是否停止刷新
    @BindingAdapter({"refreshing"})
    public static void setRefreshing(DaisyRefreshLayout refreshLayout, boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    //是否停止加载更多
    @BindingAdapter({"loadmore"})
    public static void setLoadmore(DaisyRefreshLayout refreshLayout, boolean loadmore) {
        refreshLayout.setLoadMore(loadmore);
    }
    //是否停止加载更多
    @BindingAdapter({"autorefresh"})
    public static void setAutorefresh(DaisyRefreshLayout refreshLayout, boolean autorefresh) {
        if(autorefresh){
            refreshLayout.autoRefresh();
        }
    }
}
