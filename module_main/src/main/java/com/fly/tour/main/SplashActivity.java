package com.fly.tour.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.main.mvvm.factory.MainViewModelFactory;
import com.fly.tour.main.mvvm.viewmodel.SplashViewModel;

/**
 * Description: <SplashActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class SplashActivity extends BaseMvvmActivity<ViewDataBinding,SplashViewModel> {

    @Override
    public int onBindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean enableToolbar() {
        return false;
    }

    @Override
    public void initData() {
        mViewModel.login();
    }

    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public Class<SplashViewModel> onBindViewModel() {
        return SplashViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return MainViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {
        mViewModel.getmVoidSingleLiveEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                startMainActivity();
            }
        });
    }

    @Override
    public int onBindVariableId() {
        return 0;
    }
}