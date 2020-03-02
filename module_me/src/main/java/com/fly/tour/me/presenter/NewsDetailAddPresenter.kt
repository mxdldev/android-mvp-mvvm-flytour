package com.fly.tour.me.presenter

import android.content.Context
import android.os.Handler
import com.fly.tour.common.event.me.NewsDetailCurdEvent
import com.fly.tour.common.mvp.BasePresenter
import com.fly.tour.common.util.ToastUtil
import com.fly.tour.me.contract.NewsDetailAddContract
import com.fly.tour.me.model.NewsDetailAddModel
import org.greenrobot.eventbus.EventBus

/**
 * Description: <NewsDetailAddPresenter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDetailAddPresenter(context: Context) :
    BasePresenter<NewsDetailAddModel, NewsDetailAddContract.View>(context),
    NewsDetailAddContract.Presenter {
    override fun initModel(): NewsDetailAddModel {
        return NewsDetailAddModel(mContext)
    }

    override fun addNewsDetail(type: Int, title: String, content: String) {
        Handler().postDelayed(Runnable {
            var succ = mModel?.addNewsDetail(type, title, content)
            if (succ!!) {
                ToastUtil.showToast("添加成功")
                mView?.hideTransLoadingView()
                mView?.finishActivity()
                EventBus.getDefault().post(NewsDetailCurdEvent<Any>(type))
            } else {
                ToastUtil.showToast("添加失败")
            }
        }, 1000 * 2)
    }
}