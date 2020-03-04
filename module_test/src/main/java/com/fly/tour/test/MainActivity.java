package com.fly.tour.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fly.tour.test.inject.component.DaggerMainActivityComponent;
import com.fly.tour.test.presenter.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    public MainPresenter mMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerMainActivityComponent.create().inject(this);
        mMainPresenter.prinitHelloWorld();
    }
}
