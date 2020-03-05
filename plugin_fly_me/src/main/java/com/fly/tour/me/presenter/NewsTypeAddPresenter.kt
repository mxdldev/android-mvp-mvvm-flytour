package com.fly.tour.me.presenter

import android.content.Context
import com.fly.tour.common.mvp.BasePresenter
import com.fly.tour.common.util.ToastUtil
import com.fly.tour.me.contract.NewsTypeAddContract
import com.fly.tour.me.model.NewsTypeAddModel

/**
 * Description: <NewsTypeAddPresenter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeAddPresenter(context: Context):BasePresenter<NewsTypeAddModel, NewsTypeAddContract.View>(context),NewsTypeAddContract.Presenter {
    override fun initModel(): NewsTypeAddModel {
        return NewsTypeAddModel(mContext)
    }

    override fun addNewsType(typename: String) {
        var succ = mModel?.addNewsType(typename)
        if(succ!!){
            ToastUtil.showToast("添加成功")
            mView?.finishActivity()
        }else{
            ToastUtil.showToast("添加失败")
        }
    }
}