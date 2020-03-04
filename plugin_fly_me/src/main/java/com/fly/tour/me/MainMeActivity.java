package com.fly.tour.me;


import com.fly.tour.common.base.BaseActivity;
import com.fly.tour.me.fragment.MainMeFragment;

public class MainMeActivity extends BaseActivity {

    @Override
    public int onBindLayout() {
        return R.layout.activity_me_main;
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, MainMeFragment.newInstance()).commit();
    }

    @Override
    public void initData() {

    }
}
