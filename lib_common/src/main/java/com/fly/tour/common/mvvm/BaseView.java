package com.fly.tour.common.mvvm;

import android.content.Context;

import com.fly.tour.common.mvvm.contract.ILoadView;
import com.fly.tour.common.mvvm.contract.INetErrView;
import com.fly.tour.common.mvvm.contract.INoDataView;
import com.fly.tour.common.mvvm.contract.ITransView;


/**
 * Description: <BaseView><br>
 * Author:      gxl<br>
 * Date:        2018/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface BaseView extends ILoadView, INoDataView, ITransView, INetErrView {
    void initView();
    void initListener();
    void initData();
    void finishActivity();
    Context getContext();
}
