package com.fly.tour.me.mvvm.factory;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.fly.tour.me.mvvm.model.NewsDetailAddModel;
import com.fly.tour.me.mvvm.viewmodel.NewsDetailAddViewModel;
import com.fly.tour.me.mvvm.model.NewsTypeAddModel;
import com.fly.tour.me.mvvm.model.NewsTypeListModel;
import com.fly.tour.me.mvvm.viewmodel.NewsTypeAddViewModel;
import com.fly.tour.me.mvvm.viewmodel.NewsTypeListViewModel;

/**
 * Description: <MeViewModelFactory><br>
 * Author:      mxdl<br>
 * Date:        2019/7/2<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile MeViewModelFactory INSTANCE;
    private final Application mApplication;

    public static MeViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (MeViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MeViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }
    private MeViewModelFactory(Application application) {
        this.mApplication = application;
    }
    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsTypeAddViewModel.class)) {
            return (T) new NewsTypeAddViewModel(mApplication, new NewsTypeAddModel(mApplication));
        }else if (modelClass.isAssignableFrom(NewsTypeListViewModel.class)) {
            return (T) new NewsTypeListViewModel(mApplication, new NewsTypeListModel(mApplication));
        }else if (modelClass.isAssignableFrom(NewsDetailAddViewModel.class)) {
            return (T) new NewsDetailAddViewModel(mApplication, new NewsDetailAddModel(mApplication));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
