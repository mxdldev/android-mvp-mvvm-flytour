package com.fly.tour.me.presenter;

import android.content.Context;
import android.os.Handler;

import com.fly.tour.common.event.me.NewsDetailCurdEvent;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.contract.NewsDetailAddContract;
import com.fly.tour.me.model.NewsDetailAddModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: <NewsDetailAddPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddPresenter extends BasePresenter<NewsDetailAddModel,NewsDetailAddContract.View> implements NewsDetailAddContract.Presenter {
    public NewsDetailAddPresenter(Context context) {
        super(context);
    }

    @Override
    public NewsDetailAddModel initModel() {
        return new NewsDetailAddModel(mContext);
    }

    @Override
    public void addNewsDetail(final int type, final String title, final String content) {
        mView.showTransLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean b = mModel.addNewsDetail(type, title, content);
                if(b){
                    ToastUtil.showToast("添加成功");
                    mView.hideTransLoadingView();
                    mView.finishActivity();
                    EventBus.getDefault().post(new NewsDetailCurdEvent(type));
                }else{
                    ToastUtil.showToast("添加失败");
                }
            }
        },1000 * 2);


    }
}
