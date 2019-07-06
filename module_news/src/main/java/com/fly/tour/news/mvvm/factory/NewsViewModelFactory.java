package com.fly.tour.news.mvvm.factory;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.fly.tour.news.mvvm.model.NewsDetailModel;
import com.fly.tour.news.mvvm.model.NewsListModel;
import com.fly.tour.news.mvvm.model.NewsTypeModel;
import com.fly.tour.news.mvvm.viewmodel.NewsDetailViewModel;
import com.fly.tour.news.mvvm.viewmodel.NewsListViewModel;
import com.fly.tour.news.mvvm.viewmodel.NewsTypeViewModel;

/**
 * Description: <NewsViewModelFactory><br>
 * Author:      mxdl<br>
 * Date:        2019/7/2<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile NewsViewModelFactory INSTANCE;
    private final Application mApplication;

    public static NewsViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (NewsViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewsViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }
    private NewsViewModelFactory(Application application) {
        this.mApplication = application;
    }
    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsDetailViewModel.class)) {
            return (T) new NewsDetailViewModel(mApplication, new NewsDetailModel(mApplication));
        }else if (modelClass.isAssignableFrom(NewsListViewModel.class)) {
            return (T) new NewsListViewModel(mApplication, new NewsListModel(mApplication));
        }else if (modelClass.isAssignableFrom(NewsTypeViewModel.class)) {
            return (T) new NewsTypeViewModel(mApplication, new NewsTypeModel(mApplication));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
