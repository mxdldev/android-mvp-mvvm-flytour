package com.fly.tour.common.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fly.tour.common.mvvm.viewmodel.BaseViewModel;
import com.fly.tour.common.util.log.KLog;

import java.util.Map;

/**
 * Description: <BaseMvvmActivity><br>
 * Author:      gxl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvvmActivity<VM extends BaseViewModel> extends BaseActivity {
    protected VM mViewModel;
    @Override
    public void initParam() {
        initViewModel();
        initBaseViewObservable();
        initViewObservable();
    }
    private void initViewModel() {
        mViewModel = createViewModel();
        getLifecycle().addObserver(mViewModel);
        mViewModel.injectLifecycleProvider(this);
    }
    public VM createViewModel(){
        return ViewModelProviders.of(this).get(onBindViewModel());
    }
    public abstract Class<VM> onBindViewModel();

    public abstract void initViewObservable();

    protected void initBaseViewObservable() {
        mViewModel.getUC().getShowInitLoadViewEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                 showInitLoadView(show);
            }
        });
        mViewModel.getUC().getShowTransLoadingViewEvent().observe(this,  new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                KLog.v("MYTAG","view showTransLoadingView start...");
                showTransLoadingView(show);
            }
        });
        mViewModel.getUC().getShowNoDataViewEvent().observe(this,  new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                showNoDataView(show);
            }
        });
        mViewModel.getUC().getShowNetWorkErrViewEvent().observe(this,  new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                showNetWorkErrView(show);
            }
        });
        mViewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        mViewModel.getUC().getFinishActivityEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });
        mViewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
