package com.fly.tour.common.mvvm;

import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.fly.tour.common.adapter.BaseBindingAdapter;
import com.fly.tour.common.mvvm.view.IBaseRefreshView;
import com.fly.tour.common.mvvm.viewmodel.BaseRefreshViewModel;
import com.fly.tour.common.mvvm.viewmodel.BaseRefreshViewModel1;
import com.fly.tour.common.mvvm.viewmodel.BaseViewModel;
import com.refresh.lib.BaseRefreshLayout;
import com.refresh.lib.DaisyRefreshLayout;

import java.util.List;

/**
 * Description: <下拉刷新、上拉加载更多的Activity><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvvmRefreshActivity1<T,V extends ViewDataBinding,VM extends BaseRefreshViewModel1> extends BaseMvvmActivity1<V,VM>{
    //protected DaisyRefreshLayout mRefreshLayout;
    //protected  A adapter;
    protected VM mViewModel;
     @Override
    protected void initBaseViewObservable() {
        super.initBaseViewObservable();
        initBaseViewRefreshObservable();
    }

    @Override
    public void initParam() {
        super.initParam();
     }

    private void initBaseViewRefreshObservable() {
    }

}
