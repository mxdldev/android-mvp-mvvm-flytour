package com.fly.tour.me;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.fly.tour.common.base.BaseMvpActivity;
import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.contract.NewsTypeAddContract;
import com.fly.tour.me.databinding.ActivityNewsTypeAddBinding;
import com.fly.tour.me.inject.component.DaggerNewsTypeAddComponent;
import com.fly.tour.me.inject.module.NewsTypeAddModule;
import com.fly.tour.me.model.NewsDetailAddModel;
import com.fly.tour.me.model.NewsTypeAddModel;
import com.fly.tour.me.mvvm.NewsTypeAddViewModel;
import com.fly.tour.me.presenter.NewsTypeAddPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: <NewsTypeAddActivity><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddActivity extends BaseMvvmActivity<ActivityNewsTypeAddBinding, NewsTypeAddViewModel>{


    @Override
    public int onBindLayout() {
        return R.layout.activity_news_type_add;
    }

    public void finishActivity() {
        EventBus.getDefault().post(new NewsTypeCrudEvent(EventCode.MeCode.NEWS_TYPE_ADD));
        setResult(Activity.RESULT_OK,new Intent());
        super.finishActivity();
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public NewsTypeAddViewModel initViewModel() {
        return ViewModelProviders.of(this).get(NewsTypeAddViewModel.class);
    }
}
