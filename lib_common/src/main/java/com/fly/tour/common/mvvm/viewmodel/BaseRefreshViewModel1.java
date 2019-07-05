package com.fly.tour.common.mvvm.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.fly.tour.common.binding.command.BindingAction;
import com.fly.tour.common.binding.command.BindingCommand;
import com.fly.tour.common.event.SingleLiveEvent;
import com.fly.tour.common.mvvm.model.BaseModel;

import java.util.List;

/**
 * Description: <BaseRefreshViewModel><br>
 * Author:      mxdl<br>
 * Date:        2019/06/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshViewModel1<T, M extends BaseModel> extends BaseViewModel<M> {
    protected ObservableArrayList<T> mList = new ObservableArrayList<>();
    public ObservableField<Boolean> orientation = new ObservableField();
    public ObservableField<Boolean> enableLoadMore = new ObservableField();
    public ObservableField<Boolean>  enableRefresh = new ObservableField();

    public BaseRefreshViewModel1(@NonNull Application application, M model) {
        super(application, model);
        enableLoadMore.set(enableLoadMore());
        enableRefresh.set(enableRefresh());
    }
    public boolean enableLoadMore(){
        return true;
    }
    public boolean enableRefresh(){
        return true;
    }
    protected BaseRefreshViewModel1.UIChangeRefreshLiveData mUIChangeRefreshLiveData;

    public BaseRefreshViewModel1.UIChangeRefreshLiveData getUCRefresh() {
        if (mUIChangeRefreshLiveData == null) {
            mUIChangeRefreshLiveData = new BaseRefreshViewModel1.UIChangeRefreshLiveData();
        }
        return mUIChangeRefreshLiveData;
    }

    public final class UIChangeRefreshLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Void> mStopRefresLiveEvent;
        private SingleLiveEvent<Void> mAutoRefresLiveEvent;
        private SingleLiveEvent<Void> mStopLoadMoreLiveEvent;
        public SingleLiveEvent<Void> getStopRefresLiveEvent() {
            return mStopRefresLiveEvent = createLiveData(mStopRefresLiveEvent);
        }
        public SingleLiveEvent<Void> getAutoRefresLiveEvent() {
            return mAutoRefresLiveEvent = createLiveData(mAutoRefresLiveEvent);
        }
        public SingleLiveEvent<Void> getStopLoadMoreLiveEvent() {
            return mStopLoadMoreLiveEvent = createLiveData(mStopLoadMoreLiveEvent);
        }
    }
    public void stopRefresh(){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.getStopRefresLiveEvent().call();
        }
    }
    public void autoRefresh(){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.getAutoRefresLiveEvent().call();
        }
    }
    public void stopLoadMore(){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.mStopLoadMoreLiveEvent.call();
        }
    }
    public ObservableArrayList<T> getList() {
        return mList;
    }

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            refreshData();
        }
    });
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loadMore();
        }
    });
    public BindingCommand onAutoRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            refreshData();
        }
    });

    public abstract void refreshData();

    public abstract void loadMore();

}
