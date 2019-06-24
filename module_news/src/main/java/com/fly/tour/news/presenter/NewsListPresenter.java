package com.fly.tour.news.presenter;

import android.content.Context;
import android.os.Handler;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.common.mvp.BaseRefreshPresenter;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsListContract;
import com.fly.tour.news.model.NewsListModel;

import java.util.List;

/**
 * Description: <NewsListPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListPresenter extends BaseRefreshPresenter<NewsListModel, NewsListContract.View<NewsDetail>, NewsDetail> implements NewsListContract.Presenter {
    private int newsType = 0;

    public NewsListPresenter(Context context) {
        super(context);
    }

    @Override
    public NewsListModel initModel() {
        return new NewsListModel(mContext);
    }


    @Override
    public void refreshData() {
        mView.hideNoDataView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!NetUtil.checkNetToast()){
                    mView.showNetWorkErrView();
                    return;
                }
                List<NewsDetail> datailList = mModel.getNewsType(newsType);
                if(datailList != null && datailList.size() > 0){
                    mView.refreshData(datailList);
                }else{
                    mView.showNoDataView();
                }
                mView.stopRefresh();

            }
        }, 1000 * 2);
    }

    @Override
    public void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<NewsDetail> datailList = mModel.getNewsType(newsType);
                mView.loadMoreData(datailList);
                mView.stopLoadMore();

            }
        }, 1000 * 2);
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
