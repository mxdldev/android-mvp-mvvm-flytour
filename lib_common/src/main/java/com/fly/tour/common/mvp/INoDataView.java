package com.fly.tour.common.mvp;

import android.support.annotation.DrawableRes;

/**
 * Description: <INoDataView><br>
 * Author:      mxdl<br>
 * Date:        2018/3/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface INoDataView {
    //显示无数据View
    void showNoDataView();
    //隐藏无数据View
    void hideNoDataView();

    //显示指定资源的无数据View
    void showNoDataView(@DrawableRes int resid);
}
