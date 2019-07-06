package com.fly.tour.common.mvp.view;

/**
 * Description: <ITransView><br>
 * Author:      mxdl<br>
 * Date:        2018/2/26<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface ITransView {
    //显示背景透明小菊花View,例如删除操作
    void showTransLoadingView();
    //隐藏背景透明小菊花View
    void hideTransLoadingView();
}
