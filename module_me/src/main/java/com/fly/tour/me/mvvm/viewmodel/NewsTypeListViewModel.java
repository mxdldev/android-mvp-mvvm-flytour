package com.fly.tour.me.mvvm.viewmodel;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.binding.command.BindingAction;
import com.fly.tour.common.binding.command.BindingCommand;
import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.SingleLiveEvent;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.fly.tour.common.mvvm.viewmodel.BaseRefreshViewModel1;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.mvvm.model.NewsTypeListModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <NewsTypeListViewModel><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListViewModel extends BaseRefreshViewModel1<NewsType, NewsTypeListModel> {
    private boolean isfirst = true;
    public ObservableField<Boolean> isrefresh = new ObservableField();
    public ObservableField<Boolean> isloadmore = new ObservableField();
    public ObservableField<Boolean> autorefresh = new ObservableField();
    public ObservableField<Boolean> orientation = new ObservableField();

    public NewsTypeListViewModel(@NonNull Application application, NewsTypeListModel model) {
        super(application, model);
    }

    @Override
    public void onRefreshEvent() {
        refreshData();
    }

    @Override
    public void onLoadMoreEvent() {
        loadMore();
    }

    @Override
    public void onAutoLoadEvent() {
        refreshData();
    }

    public void refreshData() {
        showNoDataView(false);
        if (isfirst) {
            showInitLoadView(true);
        }
        mModel.getListNewsType().doOnSubscribe(this).subscribe(new Observer<RespDTO<List<NewsType>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (!isfirst) {
                    isrefresh.set(true);
                }
            }

            @Override
            public void onNext(RespDTO<List<NewsType>> listRespDTO) {
                List<NewsType> listNewsType = listRespDTO.data;
                if (listNewsType != null && listNewsType.size() > 0) {
                    mList.clear();
                    mList.addAll(listNewsType);
                } else {
                    showNoDataView(true);
                }
                if (isfirst) {
                    isfirst = false;
                    showInitLoadView(false);
                } else {
                    isrefresh.set(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                showInitLoadView(false);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void loadMore() {
        mModel.getListNewsType().doOnSubscribe(this).subscribe(new Observer<RespDTO<List<NewsType>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                isloadmore.set(true);
            }

            @Override
            public void onNext(RespDTO<List<NewsType>> listRespDTO) {
                List<NewsType> listNewsType = listRespDTO.data;
                if (listNewsType != null && listNewsType.size() > 0) {
                    mList.addAll(listNewsType);
                }
                isloadmore.set(false);
            }

            @Override
            public void onError(Throwable e) {
                isloadmore.set(false);
            }

            @Override
            public void onComplete() {
            }
        });
    }


    public void deleteNewsTypeById(final int id) {
        mModel.deleteNewsTypeById(id).subscribe(new Observer<RespDTO>() {
            @Override
            public void onSubscribe(Disposable d) {
                showTransLoadingView(true);
                autorefresh.set(false);
            }

            @Override
            public void onNext(RespDTO respDTO) {
                if (respDTO.code == ExceptionHandler.APP_ERROR.SUCC) {
                    ToastUtil.showToast("删除成功");
                    autorefresh.set(true);
                    EventBus.getDefault().post(new NewsTypeCrudEvent(EventCode.MeCode.NEWS_TYPE_DELETE));
                } else {
                    ToastUtil.showToast("删除失败");
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

}
