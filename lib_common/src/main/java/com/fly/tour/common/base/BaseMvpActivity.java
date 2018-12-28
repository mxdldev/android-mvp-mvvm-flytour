package com.fly.tour.common.base;

import android.os.Bundle;

import com.fly.tour.common.mvp.BasePresenter;

/**
 * Description: <BaseMvpActivity><br>
 * Author:      gxl<br>
 * Date:        2018/6/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.dettach();
        }
        super.onDestroy();
    }
    public abstract P initPresenter();
}
