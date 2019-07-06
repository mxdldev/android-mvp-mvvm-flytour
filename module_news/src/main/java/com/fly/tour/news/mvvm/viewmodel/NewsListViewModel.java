package com.fly.tour.news.mvvm.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvvm.viewmodel.BaseRefreshViewModel;
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
public class NewsListViewModel extends BaseRefreshViewModel<NewsDetail, NewsListModel> {
    private int newsType = 0;

    public NewsListViewModel(@NonNull Application application, NewsListModel model) {
        super(application, model);
    }

    public void refreshData() {
        showNoDataView(false);
        if (!NetUtil.checkNetToast()) {
            showNetWorkErrView(true);
            return;
        }
        mModel.getListNewsByType(newsType).subscribe(new Observer<RespDTO<List<NewsDetail>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                showInitLoadView(true);
            }

            @Override
            public void onNext(RespDTO<List<NewsDetail>> listRespDTO) {
                List<NewsDetail> datailList = listRespDTO.data;
                if (datailList != null && datailList.size() > 0) {
                    mList.clear();
                    mList.addAll(datailList);
                } else {
                    showNoDataView(true);
                }
                stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                showInitLoadView(false);
            }

            @Override
            public void onComplete() {
                showInitLoadView(false);
            }
        });
    }

    @Override
    public void loadMore() {
        mModel.getListNewsByType(newsType).subscribe(new Observer<RespDTO<List<NewsDetail>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<List<NewsDetail>> listRespDTO) {
                List<NewsDetail> datailList = listRespDTO.data;
                if (datailList != null && datailList.size() > 0) {
                    mList.addAll(datailList);
                }
            }

            @Override
            public void onError(Throwable e) {
                stopLoadMore();
            }

            @Override
            public void onComplete() {
                stopLoadMore();
            }
        });
    }


    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
