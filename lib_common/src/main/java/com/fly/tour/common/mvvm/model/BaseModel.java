package com.fly.tour.common.mvvm.model;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by goldze on 2017/6/15.
 */
public class BaseModel implements IModel {
    private LifecycleProvider lifecycle;

    public void injectLifecycle(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
    }

    public LifecycleProvider getLifecycle() {
        return lifecycle;
    }
    public BaseModel() {
    }

    @Override
    public void onCleared() {

    }
}
