package com.fly.tour.me.contract;

import com.fly.tour.common.mvp.BaseView;

/**
 * Description: <NewsDetailAddContract><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsDetailAddContract {
    interface Presenter{
        void addNewsDetail(int type,String title,String content);
    }
    interface View extends BaseView {}
    interface Model{
        boolean addNewsDetail(int type,String title,String content);
    }
}
