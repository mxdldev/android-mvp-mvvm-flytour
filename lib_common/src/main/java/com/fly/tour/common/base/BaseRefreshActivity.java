package com.fly.tour.common.base;


import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.mvp.BaseView;
import com.fly.tour.common.view.CustomeSwipeRefreshLayout;

/**
 * Description: <有下拉刷新、上拉加载更多的Activity><br>
 * Author:      mxdl<br>
 * Date:        2019/1/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshActivity<V,P extends BasePresenter<V>> extends  BaseMvpActivity<V,P> implements BaseView {
    protected CustomeSwipeRefreshLayout mRefreshLayout;
    @Override
    public void showRefreshView() {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideRefreshView() {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void autoRefresh() {
        if(mRefreshLayout != null){
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    showRefreshView();
                    autoLoadData();
                }
            });
        }
    }
    @Override
    public void autoLoadData() {
    }

    @Override
    protected void initRefreshView() {
        mRefreshLayout = findViewById(onBindRreshLayout());
    }
    protected abstract int onBindRreshLayout();
}