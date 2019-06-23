package com.fly.tour.me.presenter;

import android.content.Context;
import android.os.Handler;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.event.me.NewsDetailCurdEvent;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.contract.NewsDetailAddContract;
import com.fly.tour.me.model.NewsDetailAddModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsDetailAddPresenter><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddPresenter extends BasePresenter<NewsDetailAddModel,NewsDetailAddContract.View> implements NewsDetailAddContract.Presenter {

    @Inject
    public NewsDetailAddPresenter(Context context, NewsDetailAddContract.View view, NewsDetailAddModel model) {
        super(context, view, model);
    }

    @Override
    public void addNewsDetail(final int type, final String title, final String content) {

        mModel.addNewsDetail(type,title,content)
                .subscribe(new Observer<RespDTO<NewsDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showTransLoadingView();
                    }

                    @Override
                    public void onNext(RespDTO<NewsDetail> newsDetailRespDTO) {
                        if(newsDetailRespDTO.code == ExceptionHandler.APP_ERROR.SUCC){
                            ToastUtil.showToast("添加成功");
                            mView.hideTransLoadingView();
                            mView.finishActivity();
                            EventBus.getDefault().post(new NewsDetailCurdEvent(type));
                        }else{
                            ToastUtil.showToast("添加失败");
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

    @Override
    public void getListNewsType() {
            mModel.getNewsType().subscribe(new Observer<RespDTO<List<NewsType>>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(RespDTO<List<NewsType>> listRespDTO) {
                    mView.showNewsType(listRespDTO.data);
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
