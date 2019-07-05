package com.fly.tour.common.mvvm.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

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
public class BaseRefreshViewModel<T,M extends BaseModel> extends BaseViewModel<M> {

    public BaseRefreshViewModel(@NonNull Application application, M model) {
        super(application, model);
    }
    protected UIChangeRefreshLiveData mUIChangeRefreshLiveData;

    public UIChangeRefreshLiveData getUCRefresh() {
        if (mUIChangeRefreshLiveData == null) {
            mUIChangeRefreshLiveData = new UIChangeRefreshLiveData();
        }
        return mUIChangeRefreshLiveData;
    }

    public final class UIChangeRefreshLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Void> mStopRefresLiveEvent;
        private SingleLiveEvent<Void> mAutoRefresLiveEvent;
        private SingleLiveEvent<List<T>> mRefresLiveEvent;
        private SingleLiveEvent<List<T>> mLoadMoreLiveEvent;
        private SingleLiveEvent<Void> mStopLoadMoreLiveEvent;
        public SingleLiveEvent<Void> getStopRefresLiveEvent() {
            return mStopRefresLiveEvent = createLiveData(mStopRefresLiveEvent);
        }
        public SingleLiveEvent<Void> getAutoRefresLiveEvent() {
            return mAutoRefresLiveEvent = createLiveData(mAutoRefresLiveEvent);
        }
        public SingleLiveEvent<List<T>> getRefresLiveEvent() {
            return mRefresLiveEvent = createLiveData(mRefresLiveEvent);
        }
        public SingleLiveEvent<List<T>> getLoadMoreLiveEvent() {
            return mLoadMoreLiveEvent = createLiveData(mLoadMoreLiveEvent);
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
    public void refreshData(List<T> list){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.getRefresLiveEvent().postValue(list);
        }
    }
    public void loadMore(List<T> list){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.getLoadMoreLiveEvent().postValue(list);
        }
    }

}
