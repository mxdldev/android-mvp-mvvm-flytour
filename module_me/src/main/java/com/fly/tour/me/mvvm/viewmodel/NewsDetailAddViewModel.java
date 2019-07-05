package com.fly.tour.me.mvvm.viewmodel;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.binding.command.BindingAction;
import com.fly.tour.common.binding.command.BindingCommand;
import com.fly.tour.common.event.SingleLiveEvent;
import com.fly.tour.common.event.me.NewsDetailCurdEvent;
import com.fly.tour.common.mvvm.viewmodel.BaseViewModel;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.mvvm.model.NewsDetailAddModel;

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
    public BindingCommand settingBarClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getListNewsType();
        }
    });
    public ObservableField<String> newsType = new ObservableField<>();
    public ObservableField<String> newsTitle = new ObservableField<>();
    public ObservableField<String> newsContent = new ObservableField<>();
    private NewsType mNewsType;
    public NewsDetailAddViewModel(@NonNull Application application, NewsDetailAddModel model) {
        super(application, model);
    }

    public void setNewsType(NewsType newsType) {
        mNewsType = newsType;
    }

    public void addNewsDetail() {
        if (mNewsType == null || TextUtils.isEmpty(newsType.get())) {
            ToastUtil.showToast("请选择新闻类型");
            return;
        }
        if (TextUtils.isEmpty(newsTitle.get())) {
            ToastUtil.showToast("请输入新闻标题");
            return;
        }
        if (TextUtils.isEmpty(newsContent.get())) {
            ToastUtil.showToast("请输入新闻内容");
            return;
        }

        mModel.addNewsDetail(mNewsType.getId(), newsTitle.get(), newsContent.get()).subscribe(new Observer<RespDTO<NewsDetail>>() {
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
                    EventBus.getDefault().post(new NewsDetailCurdEvent(mNewsType.getId()));
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
