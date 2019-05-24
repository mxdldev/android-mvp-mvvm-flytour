package com.fly.tour.me.contract;

import com.fly.tour.common.mvp.BaseView;

/**
 * Description: <NewsTypeContract><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsTypeContract {
    interface Presenter{
        void addNewsType(String typename);
    }
    interface View extends BaseView {

    }
    interface Model{
        boolean addNewsType(String type);
    }
}
