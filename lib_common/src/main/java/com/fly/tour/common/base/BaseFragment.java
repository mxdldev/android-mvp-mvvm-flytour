package com.fly.tour.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fly.tour.common.R;
import com.fly.tour.common.mvp.BaseView;
import com.fly.tour.common.view.CustomeSwipeRefreshLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Description: <公共的Fragment基类><br>
 * Author: gxl<br>
 * Date: 2018/6/6<br>
 * Version: V1.0.0<br>
 * Update: <br>
 *
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    protected RxAppCompatActivity mActivity;
    protected View mView;
    protected TextView mTxtTitle;
    protected Toolbar mToolbar;
    //protected CustomeSwipeRefreshLayout mRefreshLayout;//刷新控件很多页面都会用到，因此抽取取来
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RxAppCompatActivity) getActivity();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = initView(inflater, container);
        initTooBar(mView);
        //mRefreshLayout = mView.findViewById(onBindRefreshID());
        //ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    protected void initTooBar(View view) {
        mToolbar = view.findViewById(R.id.toolbar_root);
        mTxtTitle = view.findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);
            mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
        }
        if(mTxtTitle != null){
            mTxtTitle.setText(getToolbarTitle());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object obj){
    }
    public abstract View initView(LayoutInflater inflater,ViewGroup container);
    public abstract void initData(Bundle bundle);
    public String getToolbarTitle(){
        return "";
    };
    //protected abstract int onBindRefreshID();
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrNetWork() {

    }
}
