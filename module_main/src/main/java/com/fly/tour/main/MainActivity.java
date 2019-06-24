package com.fly.tour.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.fly.tour.common.base.BaseActivity;
import com.fly.tour.common.manager.NewsDBManager;
import com.fly.tour.common.provider.IFindProvider;
import com.fly.tour.common.provider.IMeProvider;
import com.fly.tour.common.provider.INewsProvider;
import com.fly.tour.main.entity.MainChannel;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Description: <BaseRefreshLayout><br>
 * Author:      mxdl<br>
 * Date:        2019/02/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends BaseActivity {
    @Autowired(name = "/news/main")
    INewsProvider mNewsProvider;

    @Autowired(name = "/find/main")
    IFindProvider mFindProvider;

    @Autowired(name = "/me/main")
    IMeProvider mMeProvider;

    private Fragment mNewsFragment;
    private Fragment mFindFragment;
    private Fragment mMeFragment;
    private Fragment mCurrFragment;

    @Override
    public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int i = menuItem.getItemId();
                if (i == R.id.navigation_trip) {
                    switchContent(mCurrFragment, mNewsFragment, MainChannel.NEWS.name);
                    mCurrFragment = mNewsFragment;

                    return true;
                } else if (i == R.id.navigation_discover) {
                    switchContent(mCurrFragment, mFindFragment, MainChannel.FIND.name);
                    mCurrFragment = mFindFragment;

                    return true;
                } else if (i == R.id.navigation_me) {
                    switchContent(mCurrFragment, mMeFragment, MainChannel.ME.name);
                    mCurrFragment = mMeFragment;

                    return true;
                }
                return false;
            }
        });
        if(mNewsProvider != null){
            mNewsFragment = mNewsProvider.getMainNewsFragment();
        }
        if(mFindProvider != null){
            mFindFragment = mFindProvider.getMainFindFragment();
        }
        if(mMeProvider != null){
            mMeFragment = mMeProvider.getMainMeFragment();
        }
        mCurrFragment = mNewsFragment;
        if(mNewsFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mNewsFragment, MainChannel.NEWS.name).commit();
        }
    }

    @Override
    public void initData() {

    }

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (from == null || to == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.frame_content, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }
}
