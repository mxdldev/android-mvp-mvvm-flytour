package com.fly.tour.news.presenter;

import android.content.Context;

import com.facebook.stetho.json.annotation.JsonProperty;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.news.contract.NewsTypeContract;
import com.fly.tour.news.model.NewsTypeModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsTypePresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/6/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypePresenter extends BasePresenter<NewsTypeModel, NewsTypeContract.View> implements NewsTypeContract.Presenter {
    @Inject
    public NewsTypePresenter(Context context, NewsTypeContract.View view, NewsTypeModel model) {
        super(context, view, model);
    }

    @Override
    public void getListNewsType() {
        mModel.getListNewsType().subscribe(new Observer<RespDTO<List<NewsType>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<List<NewsType>> listRespDTO) {
                mView.showListNewsType(listRespDTO.data);
                mView.initTabLayout();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
