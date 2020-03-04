package com.fly.tour.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fly.tour.common.base.BaseActivity;
import com.fly.tour.news.fragment.MainNewsFragment;

public class MainNewsActivity extends BaseActivity {

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_main;
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,MainNewsFragment.newInstance()).commit();
    }

    @Override
    public void initData() {

    }
}
