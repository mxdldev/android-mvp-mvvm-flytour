package com.fly.tour.common.mvp

import android.content.Context

/**
 * Description: <BaseRefreshPresenter><br>
 * Author:      mxdl<br>
 * Date:        2018/2/26<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseRefreshPresenter> */
abstract class BaseRefreshPresenter<M : BaseModel, V : BaseRefreshView<T>, T>(context: Context) : BasePresenter<M, V>(context), BaseRefreshContract.Presenter
