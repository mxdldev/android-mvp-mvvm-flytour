package com.fly.tour.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.fly.tour.common.R;
import com.fly.tour.common.view.CustomeSwipeRefreshLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Description: <公共的activity基类><br>
 * Author: gxl<br>
 * Date: 2018/6/6<br>
 * Version: V1.0.0<br>
 * Update: <br>
 *
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected static final String TAG = BaseActivity.class.getSimpleName();
    protected TextView mTxtTitle;
    protected Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onBindLayout());
        initCommonView();
        initView(savedInstanceState);
        initData();
        EventBus.getDefault().register(this);
    }

    protected CustomeSwipeRefreshLayout initRefreshLayout(){
        return null;
    }

    protected void initCommonView() {
        initToolbar();
        initRefreshView();
    }

    protected void initToolbar() {
        mToolbar = findViewById(R.id.toolbar_root);
        mTxtTitle = findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mTxtTitle != null && !TextUtils.isEmpty(title)) {
            mTxtTitle.setText(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object obj) {

    }
    protected abstract int onBindLayout();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();
    protected void initRefreshView(){};


}
