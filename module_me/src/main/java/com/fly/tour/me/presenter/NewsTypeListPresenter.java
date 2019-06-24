package com.fly.tour.me.presenter;

import android.content.Context;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.mvp.BaseRefreshPresenter;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.contract.NewsTypeListContract;
import com.fly.tour.me.model.NewsTypeListModel;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsTypeListPresenter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListPresenter extends BaseRefreshPresenter<NewsTypeListModel, NewsTypeListContract.View<NewsType>, NewsType> implements NewsTypeListContract.Presenter {

    private boolean isfirst = true;
    @Inject
    public NewsTypeListPresenter(Context context, NewsTypeListContract.View<NewsType> view, NewsTypeListModel model) {
        super(context, view, model);
    }

    @Override
    public void refreshData() {
        mView.hideNoDataView();
        if (isfirst) {
            mView.showInitLoadView();
        }
        mModel.getListNewsType().subscribe(new Observer<RespDTO<List<NewsType>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<List<NewsType>> listRespDTO) {
                List<NewsType> listNewsType = listRespDTO.data;
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

            @Override
            public void onError(Throwable e) {
                mView.hideInitLoadView();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void deleteNewsTypeById(final int id) {
        mModel.deleteNewsTypeById(id).subscribe(new Observer<RespDTO>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showTransLoadingView();
            }

            @Override
            public void onNext(RespDTO respDTO) {
                if(respDTO.code == ExceptionHandler.APP_ERROR.SUCC){
                    ToastUtil.showToast("删除成功");
                    mView.autoLoadData();
                    EventBus.getDefault().post(new NewsTypeCrudEvent(EventCode.MeCode.NEWS_TYPE_DELETE));
                }else{
                    ToastUtil.showToast("删除失败");
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
