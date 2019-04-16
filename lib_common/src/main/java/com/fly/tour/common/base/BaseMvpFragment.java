package com.fly.tour.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.common.mvp.BasePresenter;

/**
 * Description: <BaseMvpFragment><br>
 * Author:      gxl<br>
 * Date:        2018/6/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvpFragment<M extends BaseModel,V,P extends BasePresenter<M,V>> extends BaseFragment {
    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        if(mPresenter != null){
            mPresenter.attach((V) this);
            mPresenter.injectLifecycle(mActivity);
        }
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detach();
        }
        super.onDestroy();
    }
    public abstract P initPresenter();
}

