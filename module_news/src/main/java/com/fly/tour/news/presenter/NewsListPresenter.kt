package com.fly.tour.news.presenter

import android.content.Context
import android.os.Handler
import com.fly.tour.common.mvp.BaseRefreshPresenter
import com.fly.tour.common.util.NetUtil
import com.fly.tour.db.entity.NewsDetail
import com.fly.tour.news.contract.NewsListContract
import com.fly.tour.news.model.NewsListModel

/**
 * Description: <NewsListPresenter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsListPresenter(context: Context) :
    BaseRefreshPresenter<NewsListModel, NewsListContract.View<NewsDetail>, NewsDetail>(context),
    NewsListContract.Presenter {
    public var newsType = 0
        set(value) {
            field = value
        }

    override fun initModel(): NewsListModel {
        return NewsListModel(mContext)
    }

    override fun refreshData() {
        mView?.hideNoDataView()
        Handler().postDelayed(Runnable {
            if (!NetUtil.checkNet()) {
                mView?.showNetWorkErrView()
                return@Runnable
            }
            var listData = mModel?.getListNewsByType(newsType)
            if (listData != null && listData.size > 0) {
                mView?.refreshData(listData)
            } else {
                mView?.showNoDataView()
                mView?.enableRefresh(false)
                mView?.enableLoadMore(false)
            }
            mView?.stopRefresh()
        }, 2 * 1000)

    }

    override fun loadMoreData() {
        Handler().postDelayed(Runnable {
            var listData = mModel?.getListNewsByType(newsType)
            if (listData != null) {
                mView?.loadMoreData(listData)
            }
            mView?.stopLoadMore()
        }, 1000 * 2)
    }

}