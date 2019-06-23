package com.fly.tour.news.presenter;

import android.content.Context;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvp.BaseRefreshPresenter;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.news.contract.NewsListContract;
import com.fly.tour.news.model.NewsListModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsListPresenter><br>
 * Author:      gxl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListPresenter extends BaseRefreshPresenter<NewsListModel, NewsListContract.View<NewsDetail>, NewsDetail> implements NewsListContract.Presenter {
    private int newsType = 0;

    @Inject
    public NewsListPresenter(Context context, NewsListContract.View<NewsDetail> view, NewsListModel model) {
        super(context, view, model);
    }

    @Override
    public void refreshData() {
        mView.hideNoDataView();
        if (!NetUtil.checkNetToast()) {
            mView.showNetWorkErrView();
            return;
        }
        mModel.getListNewsByType(newsType).subscribe(new Observer<RespDTO<List<NewsDetail>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showInitLoadView();
            }

            @Override
            public void onNext(RespDTO<List<NewsDetail>> listRespDTO) {
                List<NewsDetail> datailList = listRespDTO.data;
                if (datailList != null && datailList.size() > 0) {
                    mView.refreshData(datailList);
                } else {
                    mView.showNoDataView();
                }
                mView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideInitLoadView();
            }

            @Override
            public void onComplete() {
                mView.hideInitLoadView();
            }
        });
    }

    @Override
    public void loadMoreData() {
        mModel.getListNewsByType(newsType).subscribe(new Observer<RespDTO<List<NewsDetail>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<List<NewsDetail>> listRespDTO) {
                List<NewsDetail> datailList = listRespDTO.data;
                if (datailList != null && datailList.size() > 0) {
                    mView.loadMoreData(datailList);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.stopLoadMore();
            }

            @Override
            public void onComplete() {
                mView.stopLoadMore();
            }
        });
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
