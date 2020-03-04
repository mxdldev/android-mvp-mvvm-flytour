package com.fly.tour.me;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.me.databinding.ActivityNewsTypeAddBinding;
import com.fly.tour.me.mvvm.factory.MeViewModelFactory;
import com.fly.tour.me.mvvm.viewmodel.NewsTypeAddViewModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: <NewsTypeAddActivity><br>
 * Author:      mxdl<br>
<<<<<<< HEAD
 * Date:        2019/5/24<br>
=======
 * Date:        2019/07/02<br>
>>>>>>> 4.1.0
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddActivity extends BaseMvvmActivity<ActivityNewsTypeAddBinding,NewsTypeAddViewModel> {

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_type_add;
    }

    @Override
    public Class<NewsTypeAddViewModel> onBindViewModel() {
        return NewsTypeAddViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return MeViewModelFactory.getInstance(getApplication());
    }
    @Override
    public void initViewObservable() {
        mViewModel.getAddNewsTypeSuccViewEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                EventBus.getDefault().post(new NewsTypeCrudEvent(EventCode.MeCode.NEWS_TYPE_ADD));
                setResult(Activity.RESULT_OK, new Intent());
                finishActivity();
            }
        });
    }

    @Override
    public int onBindVariableId() {
        return BR.viewModel;
    }

}
