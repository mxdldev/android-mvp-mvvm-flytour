package com.fly.tour.me.contract

import com.fly.tour.common.mvp.BaseView

/**
 * Description: <NewsTypeAddContract><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface NewsTypeAddContract {
    interface Presenter{
        fun addNewsType(typename: String)
    }
    interface View: BaseView{

    }
    interface Model{
        fun addNewsType(type: String): Boolean
    }
}