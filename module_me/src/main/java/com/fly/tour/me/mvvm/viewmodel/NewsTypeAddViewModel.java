package com.fly.tour.me.mvvm.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.event.SingleLiveEvent;
import com.fly.tour.common.mvvm.viewmodel.BaseViewModel;
import com.fly.tour.common.util.DateUtil;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.me.mvvm.model.NewsTypeAddModel;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
/**
 * Description: <NewsTypeAddModel><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddViewModel extends BaseViewModel<NewsTypeAddModel> {
    public static final String TAG = NewsTypeAddModel.class.getSimpleName();
    private SingleLiveEvent<Void> addNewsTypeSuccViewEvent;

    public NewsTypeAddViewModel(@NonNull Application application, NewsTypeAddModel model) {
        super(application, model);
    }

    public void addNewsType(String typename) {
        if (TextUtils.isEmpty(typename)) {
            ToastUtil.showToast("请输入新闻类型");
            return;
        }
        NewsType newsType = new NewsType();
        newsType.setTypename(typename);
        newsType.setAddtime(DateUtil.formatDate(new Date(), DateUtil.FormatType.yyyyMMddHHmmss));
        mModel.addNewsType(newsType).doOnSubscribe(this).subscribe(new Observer<RespDTO<NewsType>>() {
            @Override
            public void onSubscribe(Disposable d) {
                KLog.v("MYTAG","viewmodel showTransLoadingView start...");
                showTransLoadingView(true);
            }

            @Override
            public void onNext(RespDTO<NewsType> newsTypeRespDTO) {
                if (newsTypeRespDTO.code == ExceptionHandler.APP_ERROR.SUCC) {
                    ToastUtil.showToast("添加成功");
                    addNewsTypeSuccViewEvent.call();
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
    public SingleLiveEvent<Void> getAddNewsTypeSuccViewEvent() {
        return addNewsTypeSuccViewEvent = createLiveData(addNewsTypeSuccViewEvent);
    }
}
