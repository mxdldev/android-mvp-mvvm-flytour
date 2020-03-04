package com.fly.tour.common.mvp;

/**
 * Description: <ILoadView><br>
 * Author:      mxdl<br>
 * Date:        2018/2/26<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface ILoadView {
    //显示初始加载的View，初始进来加载数据需要显示的View
    void showInitLoadView();
    //隐藏初始加载的View
    void hideInitLoadView();
}
