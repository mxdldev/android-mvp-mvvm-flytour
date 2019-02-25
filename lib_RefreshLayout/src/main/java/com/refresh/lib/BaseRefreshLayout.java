package com.refresh.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: <BaseRefreshLayout><br>
 * Author:      gxl<br>
 * Date:        2019/2/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshLayout extends SuperSwipeRefreshLayout {
    private boolean isEnableRefresh = true;//是否启用下拉刷新
    private boolean isEnableLoadMore = true;//是否启用上拉加载更多
    protected OnRefreshListener mOnRefreshListener;//下拉刷新监听器
    protected OnLoadMoreListener mOnLoadMoreListener;//上拉加载更多监听器
    public interface OnRefreshListener{
        void onRefresh();
    }
    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }
    public BaseRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 是否启用下拉刷新
     * @param enableRefresh
     */
    public void setEnableRefresh(boolean enableRefresh) {
        isEnableRefresh = enableRefresh;
    }

    /**
     * 是否启用加载更多
     * @param enableLoadMore
     */
    public void setEnableLoadMore(boolean enableLoadMore) {
        isEnableLoadMore = enableLoadMore;
    }

    /**
     * 自动刷新
     */
    public void autoRefresh(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
                setRefreshing(true);
            }
        },1000 * 1);
    }

    /**
     * 如果禁用了加载更多则就直接返回了
     * @param ev
     * @param action
     * @return
     */
    @Override
    protected boolean handlerPushTouchEvent(MotionEvent ev, int action) {
        if (!isEnableLoadMore) {
            return false;
        }
        return super.handlerPushTouchEvent(ev,action);
    }

    /**
     * 如果禁用了就直接返回了
     * @param ev
     * @param action
     * @return
     */
    @Override
    protected boolean handlerPullTouchEvent(MotionEvent ev, int action) {
        if (!isEnableRefresh) {
            return false;
        }
        return super.handlerPullTouchEvent(ev,action);
    }
    public abstract void showRefresh();
}
