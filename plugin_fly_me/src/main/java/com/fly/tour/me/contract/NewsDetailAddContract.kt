package com.fly.tour.me.contract

import com.fly.tour.common.mvp.BaseView

/**
 * Description: <NewsDetailAddContract><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
interface NewsDetailAddContract {
    interface Presenter {
        fun addNewsDetail(type: Int, title: String, content: String)
    }

    interface View : BaseView {}
    interface Model {
        fun addNewsDetail(type: Int, title: String, content: String):Boolean
    }

}