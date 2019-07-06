package com.fly.tour.common.mvvm;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.fly.tour.common.mvvm.view.IBaseRefreshView;
import com.fly.tour.common.mvvm.viewmodel.BaseViewRefreshModel;
import com.refresh.lib.BaseRefreshLayout;
import com.refresh.lib.DaisyRefreshLayout;

import java.util.List;

/**
 * Description: <下拉刷新、上拉加载更多的Activity><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvvmRefreshActivity<T,VM extends BaseViewRefreshModel> extends BaseMvvmActivity<VM> implements IBaseRefreshView<T> {
    protected DaisyRefreshLayout mRefreshLayout;

    @Override
    protected void initCommonView() {
        super.initCommonView();
        initRefreshView();
    }

    @Override
    protected void initBaseViewObservable() {
        super.initBaseViewObservable();
        initBaseViewRefreshObservable();
    }

    private void initBaseViewRefreshObservable() {
        mViewModel.getUCRefresh().getRefresLiveEvent().observe(this, new Observer<List<T>>() {

            @Override
            public void onChanged(@Nullable List<T> list) {
                refreshData(list);
            }
        });
        mViewModel.getUCRefresh().getAutoRefresLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                autoLoadData();
            }
        });
        mViewModel.getUCRefresh().getStopRefresLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                stopRefresh();
            }
        });
        mViewModel.getUCRefresh().getLoadMoreLiveEvent().observe(this, new Observer<List<T>>() {

            @Override
            public void onChanged(@Nullable List<T> list) {
                loadMoreData(list);
            }
        });
        mViewModel.getUCRefresh().getStopLoadMoreLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                stopLoadMore();
            }
        });
    }

    public void initRefreshView() {
        mRefreshLayout = findViewById(onBindRreshLayout());
        // 下拉刷新
        mRefreshLayout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshEvent();
            }
        });
        // 上拉加载
        mRefreshLayout.setOnLoadMoreListener(new BaseRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onLoadMoreEvent();
            }
        });
        // 自动加载
        mRefreshLayout.setOnAutoLoadListener(new BaseRefreshLayout.OnAutoLoadListener() {
            @Override
            public void onAutoLoad() {
                onAutoLoadEvent();
            }
        });
    }

    protected abstract int onBindRreshLayout();

    @Override
    public void enableRefresh(boolean b) {
        mRefreshLayout.setEnableRefresh(b);
    }

    @Override
    public void enableLoadMore(boolean b) {
        mRefreshLayout.setEnableLoadMore(b);
    }

    @Override
    public void stopRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void stopLoadMore() {
        mRefreshLayout.setLoadMore(false);
    }

    @Override
    public void autoLoadData() {
        mRefreshLayout.autoRefresh();
    }
}
