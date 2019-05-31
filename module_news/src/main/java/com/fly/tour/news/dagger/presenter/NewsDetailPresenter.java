package com.fly.tour.news.dagger.presenter;

import android.content.Context;
import android.util.Log;

import com.fly.tour.common.dagger.mvp.BasePresenter;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsDetailContract;
import com.fly.tour.news.dagger.model.NewsDetailModel;

import javax.inject.Inject;

/**
 * Description: <NewsDetailPresenter><br>
 * Author:      gxl<br>
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
        KLog.v("MYTAG","context:"+mContext);
        mView.showInitLoadView();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!NetUtil.checkNetToast()){
                    mView.showNetWorkErrView();
                    return;
                }
                NewsDetail newsDetail = mModel.getNewsDetailById(id);
                if(newsDetail != null){
                    mView.showNewsDetail(newsDetail);
                }else{
                    mView.showNoDataView();
                }
                mView.hideInitLoadView();
            }
        },1000 * 2);
    }
}
