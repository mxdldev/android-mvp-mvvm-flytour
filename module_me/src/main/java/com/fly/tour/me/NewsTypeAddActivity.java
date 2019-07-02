package com.fly.tour.me;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.mvvm.factory.MeViewModelFactory;
import com.fly.tour.me.mvvm.viewmodel.NewsTypeAddViewModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: <NewsTypeAddActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddActivity extends BaseMvvmActivity<NewsTypeAddViewModel> {
    private SettingBarView mSettingBarView;
    private Button mBtnSaveNewsType;

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_type_add;
    }

    @Override
    public void initView() {
        mSettingBarView = findViewById(R.id.view_news_type);
        mSettingBarView.enableEditContext(true);
        mBtnSaveNewsType = findViewById(R.id.btn_me_save_news_type);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mBtnSaveNewsType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addNewsType(mSettingBarView.getContent());
            }
        });
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

}
