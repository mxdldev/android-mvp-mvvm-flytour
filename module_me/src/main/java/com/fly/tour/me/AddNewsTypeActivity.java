package com.fly.tour.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.fly.tour.common.base.BaseMvpActivity;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.contract.NewsTypeContract;
import com.fly.tour.me.model.NewsTypeModel;
import com.fly.tour.me.presenter.NewsTypePresenter;

/**
 * Description: <AddNewsTypeActivity><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class AddNewsTypeActivity extends BaseMvpActivity<NewsTypeModel,NewsTypeContract.View,NewsTypePresenter> implements NewsTypeContract.View{

    private SettingBarView mSettingBarView;
    private Button mBtnSaveNewsType;

    @Override
    public NewsTypePresenter initPresenter() {
        return new NewsTypePresenter(this);
    }

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
        super.finishActivity();
    }
}
