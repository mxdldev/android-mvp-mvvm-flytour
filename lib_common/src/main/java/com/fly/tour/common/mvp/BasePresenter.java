package com.fly.tour.common.mvp;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;


/**
 * Description: <BasePresenter><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BasePresenter<M extends BaseModel, V> {
    protected Context mContext;
    protected V mView;
    protected M mModel;

    public BasePresenter(Context context) {
        mContext = context;
    }

    public void attach(V view) {
        attachView(view);
        attachModel();
    }

    public void detach() {
        detachView();
        detachModel();
    }

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }

    public void attachModel() {
        mModel = initModel();
    }

    public void detachModel() {
        mModel.destory();
        mModel = null;
    }

    public abstract M initModel();

    public void injectLifecycle(LifecycleProvider lifecycle) {
        if (mModel != null) {
            mModel.injectLifecycle(lifecycle);
        }
    }
}
