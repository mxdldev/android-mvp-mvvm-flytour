package com.fly.tour.me;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.fly.tour.common.base.BaseMvpActivity;
import com.fly.tour.common.event.EventCode;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.contract.NewsTypeAddContract;
import com.fly.tour.me.model.NewsTypeAddModel;
import com.fly.tour.me.presenter.NewsTypeAddPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: <NewsTypeAddActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddActivity extends BaseMvpActivity<NewsTypeAddModel,NewsTypeAddContract.View, NewsTypeAddPresenter> implements NewsTypeAddContract.View{

    private SettingBarView mSettingBarView;
    private Button mBtnSaveNewsType;

    @Override
    public NewsTypeAddPresenter initPresenter() {
        return new NewsTypeAddPresenter(this);
    }

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_type_add;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void initView() {
        mSettingBarView = findViewById(R.id.view_news_type);
        mSettingBarView.enableEditContext(true);
        mBtnSaveNewsType = findViewById(R.id.btn_me_save_news_type);
    }

    @Override
    public void initListener() {
        mBtnSaveNewsType.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String content = mSettingBarView.getContent();
                if(TextUtils.isEmpty(content)){
                    ToastUtil.showToast("请输入新闻类型");
                    return;
                }
                mPresenter.addNewsType(content);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void finishActivity() {
        EventBus.getDefault().post(new NewsTypeCrudEvent(EventCode.MeCode.NEWS_TYPE_ADD));
        setResult(Activity.RESULT_OK,new Intent());
        super.finishActivity();
    }
}
