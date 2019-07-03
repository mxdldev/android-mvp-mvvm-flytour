package com.fly.tour.me.mvvm.model;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.event.SingleLiveEvent;
import com.fly.tour.common.event.me.NewsDetailCurdEvent;
import com.fly.tour.common.mvvm.viewmodel.BaseViewModel;
import com.fly.tour.common.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsDetailAddPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddViewModel extends BaseViewModel<NewsDetailAddModel> {
    private SingleLiveEvent<List<NewsType>> mSingleNewsTypeLiveEvent;
    public NewsDetailAddViewModel(@NonNull Application application, NewsDetailAddModel model) {
        super(application, model);
    }

    public void addNewsDetail(final int type, final String title, final String content) {

        mModel.addNewsDetail(type, title, content).subscribe(new Observer<RespDTO<NewsDetail>>() {
            @Override
            public void onSubscribe(Disposable d) {
                showTransLoadingView(true);
            }

            @Override
            public void onNext(RespDTO<NewsDetail> newsDetailRespDTO) {
                if (newsDetailRespDTO.code == ExceptionHandler.APP_ERROR.SUCC) {
                    ToastUtil.showToast("添加成功");
                    showTransLoadingView(false);
                    finishActivity();
                    EventBus.getDefault().post(new NewsDetailCurdEvent(type));
                } else {
                    ToastUtil.showToast("添加失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                showTransLoadingView(false);
            }

            @Override
            public void onComplete() {
                showTransLoadingView(false);
            }
        });
    }

    public void getListNewsType() {
        mModel.getNewsType().subscribe(new Observer<RespDTO<List<NewsType>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<List<NewsType>> listRespDTO) {
                getSingleNewsTypeLiveEvent().postValue(listRespDTO.data);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    public SingleLiveEvent<List<NewsType>> getSingleNewsTypeLiveEvent() {
        return mSingleNewsTypeLiveEvent = createLiveData(mSingleNewsTypeLiveEvent);
    }
}
