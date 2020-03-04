package com.fly.tour.me.presenter;

import android.content.Context;

import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.mvp.BaseRefreshPresenter;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.me.contract.NewsTypeListContract;
import com.fly.tour.me.model.NewsTypeListModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Description: <NewsTypeListPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListPresenter extends BaseRefreshPresenter<NewsTypeListModel, NewsTypeListContract.View<NewsType>, NewsType> implements NewsTypeListContract.Presenter {
    public NewsTypeListPresenter(Context context) {
        super(context);
    }

    private boolean isfirst = true;

    @Override
    public NewsTypeListModel initModel() {
        return new NewsTypeListModel(mContext);
    }

    @Override
    public void refreshData() {
        mView.hideNoDataView();
        if (isfirst) {
            mView.showInitLoadView();
        }else{
            mView.autoLoadData();
        }
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<NewsType> listNewsType = mModel.getListNewsType();
                KLog.v("MYTAG","listNewsType:"+listNewsType.toString());
                if (listNewsType != null && listNewsType.size() > 0) {
                    mView.refreshData(listNewsType);
                }else{
                    mView.showNoDataView();
                }
                if (isfirst) {
                    isfirst = false;
                    mView.hideInitLoadView();
                } else {
                    mView.stopRefresh();
                }
            }
        }, 1000 * 2);

    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void deleteNewsTypeById(final int id) {
        mView.showTransLoadingView();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mModel.deleteNewsTypeById(id)){
                    ToastUtil.showToast("删除成功");
                    refreshData();
                    EventBus.getDefault().post(new NewsTypeCrudEvent(EventCode.MeCode.NEWS_TYPE_DELETE));
                }else{
                    ToastUtil.showToast("删除失败");
                }
                mView.hideTransLoadingView();
            }
        },1000 * 2);
    }
}
