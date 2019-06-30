package com.fly.tour.main;

import android.content.Intent;

import com.fly.tour.common.base.BaseMvpActivity;
import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.main.contract.SplashContract;
import com.fly.tour.main.inject.component.DaggerSplashComponent;
import com.fly.tour.main.inject.module.SplashModule;
import com.fly.tour.main.model.SplashModel;
import com.fly.tour.main.presenter.SplashPresenter;

/**
 * Description: <SplashActivity><br>
 * Author:      gxl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class SplashActivity extends BaseMvpActivity<SplashModel, SplashContract.View, SplashPresenter> implements SplashContract.View {
    @Override
    public void injectPresenter() {
        DaggerSplashComponent.builder().splashModule(new SplashModule(this)).build().inject(this);
    }

    @Override
    public int onBindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mPresenter.login();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}