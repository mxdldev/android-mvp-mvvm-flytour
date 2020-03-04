package com.fly.tour.find;

import com.fly.tour.common.base.BaseActivity;
import com.fly.tour.find.fragment.MainFindFragment;

public class MainFindActivity extends BaseActivity {


    @Override
    public int onBindLayout() {
        return R.layout.activity_find_main;
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, MainFindFragment.newInstance()).commit();
    }

    @Override
    public void initData() {

    }
}
