package com.fly.tour.common.mvp

/**
 * Description: <BaseView><br>
 * Author:      mxdl<br>
 * Date:        2018/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseView> */
interface BaseView : ILoadView, INoDataView, ITransView, INetErrView {
    fun initListener()
    fun initData()
    fun finishActivity()
}
