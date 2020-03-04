package com.fly.tour.common.mvvm;

import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;

import com.fly.tour.common.adapter.BaseAdapter;
import com.fly.tour.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.fly.tour.common.util.log.KLog;
import com.refresh.lib.DaisyRefreshLayout;

/**
 * Description: <下拉刷新、上拉加载更多的Fragment><br>
 * Author:      mxdl<br>
 * Date:        2018/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvvmRefreshFragment<T, V extends ViewDataBinding, VM extends BaseRefreshViewModel> extends BaseMvvmFragment<V, VM> {
    protected DaisyRefreshLayout mRefreshLayout;
    protected BaseAdapter.OnItemClickListener mItemClickListener;
    protected BaseAdapter.OnItemLongClickListener mOnItemLongClickListener;
    @Override
    public void initCommonView(View view) {
        super.initCommonView(view);
        initRefreshView();
    }

    @Override
    protected void initBaseViewObservable() {
        super.initBaseViewObservable();
        initBaseViewRefreshObservable();
    }

    private void initBaseViewRefreshObservable() {

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
        mViewModel.getUCRefresh().getStopLoadMoreLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                stopLoadMore();
            }
        });
    }

    public abstract DaisyRefreshLayout getRefreshLayout();

    public void initRefreshView() {
        mRefreshLayout = getRefreshLayout();
    }

    public void stopRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    public void stopLoadMore() {
        mRefreshLayout.setLoadMore(false);
    }

    public void autoLoadData() {
        KLog.v("MYTAG", "autoLoadData start...");
        if (mRefreshLayout != null) {
            KLog.v("MYTAG", "autoLoadData1 start...");
            mRefreshLayout.autoRefresh();
        }
    }
    public void setItemClickListener(BaseAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(BaseAdapter.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }
}
