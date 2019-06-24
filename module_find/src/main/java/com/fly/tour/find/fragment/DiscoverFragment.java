package com.fly.tour.find.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.find.R;
import com.refresh.lib.ArrowRefreshLayout;
import com.refresh.lib.DaisyRefreshLayout;


/**
 * Description: <发现Fragment><br>
 * Author:      mxdl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class DiscoverFragment extends BaseFragment {
    public static final String TAG = DiscoverFragment.class.getSimpleName();
    private DaisyRefreshLayout mSuperSwipeRefreshLayout;
    private RecyclerView mRecView;

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_discover_main, container, false);
        mSuperSwipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        mSuperSwipeRefreshLayout.setOnRefreshListener(new ArrowRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v(TAG,"refresh start");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.v(TAG,"response ok");
                        mSuperSwipeRefreshLayout.setRefreshing(false);
                    }
                },1000 * 5);
            }
        });
        mSuperSwipeRefreshLayout.setOnLoadMoreListener(new ArrowRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.v(TAG,"loadMore start");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.v(TAG,"response ok");
                        mSuperSwipeRefreshLayout.setLoadMore(false);
                    }
                },1000 * 5);
            }
        });
//        mSuperSwipeRefreshLayout.autoRefresh();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.v(TAG,"response ok");
//                mSuperSwipeRefreshLayout.setRefreshing(false);
//            }
//        },1000 * 5);
       // mSuperSwipeRefreshLayout.setEnableLoadMore(false);
        //mSuperSwipeRefreshLayout.setEnableRefresh(false);
        return view;
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
