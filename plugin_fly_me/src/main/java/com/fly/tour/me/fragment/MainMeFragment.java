package com.fly.tour.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.event.RequestCode;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.NewsDetailAddActivity;
import com.fly.tour.me.NewsTypeAddActivity;
import com.fly.tour.me.NewsTypeListActivity;
import com.fly.tour.me.PluginArouterActivity;
import com.fly.tour.me.R;


/**
 * Description: <MainMeFragment><br>
 * Author:      mxdl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainMeFragment extends BaseFragment{

    private SettingBarView mSetNewsType;
    private SettingBarView mSetNewsDetail;

    public static MainMeFragment newInstance() {
        return new MainMeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public int onBindLayout() {
        return R.layout.fragment_me_main;
    }

    @Override
    public void initView(View view) {
        mSetNewsType = view.findViewById(R.id.view_setting_news_type);
        mSetNewsDetail = view.findViewById(R.id.view_setting_news_detail);
    }

    @Override
    public void initListener() {
        mSetNewsType.setOnClickSettingBarViewListener(new SettingBarView.OnClickSettingBarViewListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(mActivity, NewsTypeListActivity.class));
            }
        });
        mSetNewsDetail.setOnClickSettingBarViewListener(new SettingBarView.OnClickSettingBarViewListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(mActivity, NewsDetailAddActivity.class));
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public String getToolbarTitle() {
        return "我的";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_add) {
              startActivity(new Intent(mActivity, PluginArouterActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
