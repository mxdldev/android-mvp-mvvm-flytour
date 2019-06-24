package com.fly.tour.common.mvp;

import android.content.Context;

/**
 * Description: <BaseRefreshPresenter><br>
 * Author:      mxdl<br>
 * Date:        2018/2/26<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshPresenter<M extends BaseModel,V extends BaseRefreshView<T>,T> extends BasePresenter<M,V> implements BaseRefreshContract.Presenter{

    public BaseRefreshPresenter(Context context, V view, M model) {
        super(context, view, model);
    }
}
