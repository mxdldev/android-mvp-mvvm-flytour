package com.fly.tour.common.dagger.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fly.tour.common.dagger.mvp.BaseModel;
import com.fly.tour.common.dagger.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Description: <BaseMvpActivity><br>
 * Author:      gxl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvpActivity<M extends BaseModel,V,P extends BasePresenter<M,V>> extends BaseActivity {
    @Inject
    public P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectPresenter();
        if(mPresenter != null){
            mPresenter.injectLifecycle(this);
        }
        super.onCreate(savedInstanceState);

    }
    public abstract void injectPresenter();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detach();
        }
    }
}
