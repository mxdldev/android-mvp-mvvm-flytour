package com.fly.tour.news.presenter;

import android.content.Context;

import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsDetailContract;
import com.fly.tour.news.model.NewsDetailModel;

/**
 * Description: <NewsDetailPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailPresenter extends BasePresenter<NewsDetailModel,NewsDetailContract.View> implements NewsDetailContract.Presenter{
    public NewsDetailPresenter(Context context) {
        super(context);
    }

    @Override
    public NewsDetailModel initModel() {
        return new NewsDetailModel(mContext);
    }

    @Override
    public void getNewsDetailById(final int id) {
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
