package com.fly.tour.find.fragment;

import android.view.View;
import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.find.R;

/**
 * Description: <发现Fragment><br>
 * Author:      gxl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class DiscoverFragment extends BaseFragment {
    public static final String TAG = DiscoverFragment.class.getSimpleName();
    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }
    @Override
    public int onBindLayout() {
        return R.layout.fragment_discover_main;
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
