package com.fly.tour.common.mvp;

/**
 * Description: <MVP基本交互协议><br>
 * Author:      gxl<br>
 * Date:        2018/12/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface BaseContract {
    interface Presenter<V>{
        void attach(V view);
        void dettach();
    }
    interface View{
        void showLoading();
        void hideLoading();
        void showErrNetWork();
    }
}
