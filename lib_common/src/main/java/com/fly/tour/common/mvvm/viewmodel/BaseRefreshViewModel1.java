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
    public ObservableField<Boolean> isrefresh = new ObservableField();
    public ObservableField<Boolean> isloadmore = new ObservableField();
    public ObservableField<Boolean> autorefresh = new ObservableField();
    public ObservableField<Boolean> orientation = new ObservableField();

    public BaseRefreshViewModel1(@NonNull Application application, M model) {
        super(application, model);
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
