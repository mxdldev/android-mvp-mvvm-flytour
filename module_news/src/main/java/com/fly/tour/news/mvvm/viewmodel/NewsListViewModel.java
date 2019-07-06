package com.fly.tour.news.mvvm.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.NewsDetail;
import com.fly.tour.common.mvvm.viewmodel.BaseViewRefreshModel;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.news.mvvm.model.NewsListModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsListPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListViewModel extends BaseViewRefreshModel<NewsDetail, NewsListModel> {
    private int newsType = 0;

    public NewsListViewModel(@NonNull Application application, NewsListModel model) {
        super(application, model);
    }

    public void refreshData() {
        postShowNoDataViewEvent(false);
        if (!NetUtil.checkNetToast()) {
            postShowNetWorkErrViewEvent(true);
            return;
        }
        mModel.getListNewsByType(newsType).subscribe(new Observer<RespDTO<List<NewsDetail>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                postShowInitLoadViewEvent(true);
            }

            @Override
            public void onNext(RespDTO<List<NewsDetail>> listRespDTO) {
                List<NewsDetail> datailList = listRespDTO.data;
                if (datailList != null && datailList.size() > 0) {
                    postRefreshDataEvent(datailList);
                } else {
                    postShowNoDataViewEvent(true);
                }
                postStopRefreshEvent();
            }

            @Override
            public void onError(Throwable e) {
                postShowInitLoadViewEvent(false);
            }

            @Override
            public void onComplete() {
                postShowInitLoadViewEvent(false);
            }
        });
    }

    public void loadMoreData() {
        mModel.getListNewsByType(newsType).subscribe(new Observer<RespDTO<List<NewsDetail>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<List<NewsDetail>> listRespDTO) {
                List<NewsDetail> datailList = listRespDTO.data;
                if (datailList != null && datailList.size() > 0) {
                    postLoadMoreEvent(datailList);
                }
            }

            @Override
            public void onError(Throwable e) {
                postStopLoadMoreEvent();
            }

            @Override
            public void onComplete() {
                postStopLoadMoreEvent();
            }
        });
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
