package com.fly.tour.common.mvp;

/**
 * Description: <BaseView><br>
 * Author:      mxdl<br>
 * Date:        2018/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface BaseView extends ILoadView,INoDataView,ITransView,INetErrView{
    void initView();
    void initListener();
    void initData();
    void finishActivity();
}
