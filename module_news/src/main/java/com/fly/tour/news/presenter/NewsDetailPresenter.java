package com.fly.tour.news.presenter;

import android.content.Context;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.news.contract.NewsDetailContract;
import com.fly.tour.news.model.NewsDetailModel;

import java.util.logging.Handler;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsDetailPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailPresenter extends BasePresenter<NewsDetailModel,NewsDetailContract.View> implements NewsDetailContract.Presenter{

    @Inject
    public NewsDetailPresenter(Context context, NewsDetailContract.View view, NewsDetailModel model) {
        super(context, view, model);
    }

    @Override
    public void getNewsDetailById(final int id) {
        if(!NetUtil.checkNetToast()){
            mView.showNetWorkErrView();
            return;
        }
        mModel.getNewsDetailById(id).subscribe(new Observer<RespDTO<NewsDetail>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showInitLoadView();
            }

            @Override
            public void onNext(RespDTO<NewsDetail> newsDetailRespDTO) {
                NewsDetail newsDetail = newsDetailRespDTO.data;
                if(newsDetail != null){
                    mView.showNewsDetail(newsDetail);
                }else{
                    mView.showNoDataView();
                }
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
}
