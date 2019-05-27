package com.fly.tour.me.fragment;

import android.content.Intent;
import android.view.View;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.NewsDetailAddActivity;
import com.fly.tour.me.NewsTypeAddActivity;
import com.fly.tour.me.NewsTypeListActivity;
import com.fly.tour.me.R;


/**
 * Description: <MeFragment><br>
 * Author:      gxl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MeFragment extends BaseFragment{

    private SettingBarView mSetNewsType;
    private SettingBarView mSetNewsDetail;

    public static MeFragment newInstance() {
        return new MeFragment();
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
                startActivity(new Intent(mActivity,NewsTypeListActivity.class));
            }
        });
        mSetNewsDetail.setOnClickSettingBarViewListener(new SettingBarView.OnClickSettingBarViewListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(mActivity,NewsDetailAddActivity.class));
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


}
