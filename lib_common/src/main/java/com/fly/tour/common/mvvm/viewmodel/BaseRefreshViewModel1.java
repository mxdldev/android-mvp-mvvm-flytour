package com.fly.tour.common.mvvm.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
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
public abstract class BaseRefreshViewModel1<T,M extends BaseModel> extends BaseViewModel<M> {
    protected ObservableArrayList<T> mList = new ObservableArrayList<>();
    public BaseRefreshViewModel1(@NonNull Application application, M model) {
        super(application, model);
    }

    public ObservableArrayList<T> getList() {
        return mList;
    }

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onRefreshEvent();
        }
    });
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onLoadMoreEvent();
        }
    });
    public BindingCommand onAutoRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          onAutoLoadEvent();
        }
    });
    public abstract void onRefreshEvent();

    public abstract void onLoadMoreEvent();

    public abstract void onAutoLoadEvent();

    //    public UIChangeRefreshLiveData getUCRefresh() {
//        if (mUIChangeRefreshLiveData == null) {
//            mUIChangeRefreshLiveData = new UIChangeRefreshLiveData();
//        }
//        return mUIChangeRefreshLiveData;
//    }

//    public final class UIChangeRefreshLiveData extends SingleLiveEvent {
//        private SingleLiveEvent<Void> mStopRefresLiveEvent;
//        private SingleLiveEvent<Void> mAutoRefresLiveEvent;
//        private SingleLiveEvent<List<T>> mRefresLiveEvent;
//        private SingleLiveEvent<List<T>> mLoadMoreLiveEvent;
//        private SingleLiveEvent<Void> mStopLoadMoreLiveEvent;
//        public SingleLiveEvent<Void> getStopRefresLiveEvent() {
//            return mStopRefresLiveEvent = createLiveData(mStopRefresLiveEvent);
//        }
//        public SingleLiveEvent<Void> getAutoRefresLiveEvent() {
//            return mAutoRefresLiveEvent = createLiveData(mAutoRefresLiveEvent);
//        }
//        public SingleLiveEvent<List<T>> getRefresLiveEvent() {
//            return mRefresLiveEvent = createLiveData(mRefresLiveEvent);
//        }
//        public SingleLiveEvent<List<T>> getLoadMoreLiveEvent() {
//            return mLoadMoreLiveEvent = createLiveData(mLoadMoreLiveEvent);
//        }
//        public SingleLiveEvent<Void> getStopLoadMoreLiveEvent() {
//            return mStopLoadMoreLiveEvent = createLiveData(mStopLoadMoreLiveEvent);
//        }
//    }
//    public void stopRefresh(){
//        if(mUIChangeRefreshLiveData != null){
//            mUIChangeRefreshLiveData.getStopRefresLiveEvent().call();
//        }
//    }
//    public void autoRefresh(){
//        if(mUIChangeRefreshLiveData != null){
//            mUIChangeRefreshLiveData.getAutoRefresLiveEvent().call();
//        }
//    }
//    public void refreshData(List<T> list){
//        if(mUIChangeRefreshLiveData != null){
//            mUIChangeRefreshLiveData.getRefresLiveEvent().postValue(list);
//        }
//    }
//    public void loadMore(List<T> list){
//        if(mUIChangeRefreshLiveData != null){
//            mUIChangeRefreshLiveData.getLoadMoreLiveEvent().postValue(list);
//        }
//    }
//    public void stopLoadMore(){
//        if(mUIChangeRefreshLiveData != null){
//            mUIChangeRefreshLiveData.mStopLoadMoreLiveEvent.call();
//        }
//    }
}
