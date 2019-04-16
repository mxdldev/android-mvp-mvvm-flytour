package com.fly.tour.common.base;


import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.mvp.BaseRefreshPresenter;
import com.fly.tour.common.mvp.BaseRefreshView;
import com.fly.tour.common.mvp.BaseView;
import com.fly.tour.common.view.CustomeSwipeRefreshLayout;
import com.refresh.lib.BaseRefreshLayout;
import com.refresh.lib.DaisyRefreshLayout;

/**
 * Description: <有下拉刷新、上拉加载更多的Activity><br>
 * Author:      gxl<br>
 * Date:        2019/1/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshActivity<M extends BaseModel, V extends BaseRefreshView<T>, P extends BaseRefreshPresenter<M, V, T>, T> extends BaseMvpActivity<M, V, P> implements BaseRefreshView<T> {
    protected DaisyRefreshLayout mRefreshLayout;

    @Override
    protected void initCommonView() {
        super.initCommonView();
        initRefreshView();
    }

    public void initRefreshView() {
        mRefreshLayout = findViewById(onBindRreshLayout());
        mRefreshLayout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshEvent();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new BaseRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onLoadMoreEvent();
            }
        });
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