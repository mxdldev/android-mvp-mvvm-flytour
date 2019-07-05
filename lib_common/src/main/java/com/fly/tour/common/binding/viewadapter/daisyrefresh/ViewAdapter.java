package com.fly.tour.common.binding.viewadapter.daisyrefresh;

import android.databinding.BindingAdapter;
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
    @BindingAdapter(value = {"onRefreshCommand","onLoadMoreCommand","onAutoRefreshCommand"},requireAll = false)
    public static void onRefreshCommand(DaisyRefreshLayout refreshLayout, final BindingCommand onRefreshCommand,final BindingCommand onLoadMoreCommond,final BindingCommand onAutoRerefeshCommond) {
        refreshLayout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new BaseRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (onLoadMoreCommond != null) {
                    onLoadMoreCommond.execute();
                }
            }
        });
        refreshLayout.setOnAutoLoadListener(new BaseRefreshLayout.OnAutoLoadListener() {
            @Override
            public void onAutoLoad() {
                if (onAutoRerefeshCommond != null) {
                    onAutoRerefeshCommond.execute();
                }
            }
        });
    }
}
