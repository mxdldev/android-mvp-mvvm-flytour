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

    public BasePresenter(Context context, V view, M model) {
        mContext = context;
        mView = view;
        mModel = model;
    }
    public void detach() {
        detachView();
        detachModel();
    }
    public void detachView() {
        mView = null;
    }


    public void detachModel() {
        mModel.destory();
        mModel = null;
    }

    public void injectLifecycle(LifecycleProvider lifecycle) {
        if (mModel != null) {
            mModel.injectLifecycle(lifecycle);
        }
    }
}
