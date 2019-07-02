package com.fly.tour.common.mvvm;

import android.content.Context;

/**
 * Description: <BaseView><br>
 * Author:      mxdl<br>
 * Date:        2019/06/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface BaseView{
    void initView();
    void initListener();
    void initData();
    void finishActivity();
    void showInitLoadView(boolean show);
    void showNoDataView(boolean show);
    void showTransLoadingView(boolean show);
    void showNetWorkErrView(boolean show);
    Context getContext();
}
