package com.fly.tour.me.presenter;

import android.content.Context;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.util.DateUtil;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.contract.NewsTypeAddContract;
import com.fly.tour.me.model.NewsTypeAddModel;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsTypeAddPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddPresenter extends BasePresenter<NewsTypeAddModel,NewsTypeAddContract.View> implements NewsTypeAddContract.Presenter {

    @Inject
    public NewsTypeAddPresenter(Context context, NewsTypeAddContract.View view, NewsTypeAddModel model) {
        super(context, view, model);
    }

    @Override
    public void addNewsType(String typename) {
        NewsType newsType = new NewsType();
        newsType.setTypename(typename);
        newsType.setAddtime(DateUtil.formatDate(new Date(), DateUtil.FormatType.yyyyMMddHHmmss));
        mModel.addNewsType(newsType).subscribe(new Observer<RespDTO<NewsType>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showTransLoadingView();
            }

            @Override
            public void onNext(RespDTO<NewsType> newsTypeRespDTO) {
                if(newsTypeRespDTO.code == ExceptionHandler.APP_ERROR.SUCC){
                    ToastUtil.showToast("添加成功");
                    mView.finishActivity();
                }else{
                    ToastUtil.showToast("添加失败");
                }

            }

            @Override
            public void onError(Throwable e) {
                mView.hideTransLoadingView();
            }

            @Override
            public void onComplete() {
                mView.hideTransLoadingView();
            }
        });
    }
}
