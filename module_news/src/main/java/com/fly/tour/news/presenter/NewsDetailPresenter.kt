package com.fly.tour.news.presenter

import android.content.Context
import android.os.Handler
import com.fly.tour.common.mvp.BasePresenter
import com.fly.tour.common.util.NetUtil
import com.fly.tour.news.contract.NewsDetailContract
import com.fly.tour.news.model.NewsDetailModel

/**
 * Description: <NewsDetailPresenter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDetailPresenter(context: Context): BasePresenter<NewsDetailModel,NewsDetailContract.View>(context),NewsDetailContract.Presenter{
    override fun initModel(): NewsDetailModel {
        return NewsDetailModel(mContext)
    }

    override fun getNewsDetailById(id: Int) {
        mView?.showInitLoadView()
        Handler().postDelayed(Runnable {
            if(!NetUtil.checkNetToast()){
                mView?.showNetWorkErrView()
                return@Runnable
            }
            var newsDetail = mModel?.getNewsDetailById(id)
            if(newsDetail != null){
                mView?.showViewDetail(newsDetail)
            }else{
                mView?.showNoDataView()
            }
            mView?.hideInitLoadView()
        },1000 * 2)
    }
}