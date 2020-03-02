package com.fly.tour.me.presenter

import android.content.Context
import android.os.Handler
import com.fly.tour.common.event.EventCode
import com.fly.tour.common.event.me.NewsTypeCrudEvent
import com.fly.tour.common.mvp.BaseRefreshPresenter
import com.fly.tour.common.util.ToastUtil
import com.fly.tour.db.entity.NewsType
import com.fly.tour.me.contract.NewsTypeListContract
import com.fly.tour.me.model.NewsTypeListModel
import org.greenrobot.eventbus.EventBus

/**
 * Description: <NewsTypeListPresenter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeListPresenter(context: Context) :
    BaseRefreshPresenter<NewsTypeListModel, NewsTypeListContract.View<NewsType>, NewsType>(context),
    NewsTypeListContract.Presenter {
    private var isFirst = true;
    override fun initModel(): NewsTypeListModel {
        return NewsTypeListModel(mContext)
    }

    override fun refreshData() {
        mView?.hideNoDataView()
        if (isFirst) {
            mView?.showInitLoadView()
        } else {
            mView?.autoLoadData()
        }
        Handler().postDelayed(Runnable {
            var listNewsType = mModel?.getListNewsType()
            if (listNewsType != null && listNewsType.size > 0) {
                mView?.refreshData(listNewsType)
            } else {
                mView?.showNoDataView()
            }
            if (isFirst) {
                isFirst = false
                mView?.hideInitLoadView()
            } else {
                mView?.stopRefresh()
            }
        }, 1000 * 2)
    }

    override fun loadMoreData() {
    }

    override fun deleteNewsTypeById(id: Int) {
        mView?.showTransLoadingView()
        Handler().postDelayed(Runnable {
            if (mModel!!.deleteNewsTypeById(id)) {
                ToastUtil.showToast("删除成功")
                refreshData()
                EventBus.getDefault()
                    .post(NewsTypeCrudEvent<Any>(EventCode.MeCode.NEWS_TYPE_DELETE))
            } else {
                ToastUtil.showToast("删除失败")
            }
            mView?.hideTransLoadingView()
        }, 1000 * 2)
    }

}