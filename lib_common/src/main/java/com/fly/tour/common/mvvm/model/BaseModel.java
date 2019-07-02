package com.fly.tour.common.mvvm.model;

import android.app.Application;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Description: <BaseModel><br>
 * Author:      mxdl<br>
 * Date:        2019/06/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseModel implements IModel {
    protected Application mApplication;
    private CompositeDisposable mCompositeDisposable;
    public BaseModel(Application application) {
        mApplication = application;
        mCompositeDisposable = new CompositeDisposable();
    }
    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onCleared() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
