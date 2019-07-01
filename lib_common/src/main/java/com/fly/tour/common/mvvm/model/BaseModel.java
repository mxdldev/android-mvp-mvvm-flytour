package com.fly.tour.common.mvvm.model;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by goldze on 2017/6/15.
 */
public abstract class BaseModel implements IModel {
    private LifecycleProvider mLifecycleProvider;

    public void injectLifecycle(LifecycleProvider lifecycle) {
        this.mLifecycleProvider = lifecycle;
    }

    public LifecycleProvider getLifecycleProvider() {
        return mLifecycleProvider;
    }

}
