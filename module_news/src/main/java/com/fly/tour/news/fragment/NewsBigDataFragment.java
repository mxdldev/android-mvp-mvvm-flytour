package com.fly.tour.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.trip.R;


/**
 * Description: <大数据><br>
 * Author:      gxl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsBigDataFragment extends BaseFragment {
    public static NewsBigDataFragment newInstance() {
        return new NewsBigDataFragment();
    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_trip_team;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getToolbarTitle() {
        return null;
    }
}
