package com.fly.tour.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.base.BaseAdapter;
import com.fly.tour.common.base.BaseMvpActivity;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.contract.NewsDetailAddContract;
import com.fly.tour.me.inject.component.DaggerNewsDetailAddComponent;
import com.fly.tour.me.inject.module.NewsDetailAddModule;
import com.fly.tour.me.model.NewsDetailAddModel;
import com.fly.tour.me.presenter.NewsDetailAddPresenter;
import com.fly.tour.me.view.NewsTypeBottomSelectDialog;

/**
 * Description: <NewsDetailAddActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/6/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddActivity extends BaseMvpActivity<NewsDetailAddModel,NewsDetailAddContract.View,NewsDetailAddPresenter> implements NewsDetailAddContract.View {

    private SettingBarView mViewSetNewsType;
    private SettingBarView mViewSetNewsTitle;
    //private SettingBarView mViewSetNewsContent;
    private Button mBtnSaveNewsConent;
    private NewsType mNewsType;
    private EditText mTxtNewsDetail;

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_detail_add;
    }

    @Override
    public void initView() {
        mViewSetNewsType = findViewById(R.id.view_me_set_news_type);
        mViewSetNewsTitle = findViewById(R.id.view_me_set_news_title);
        //mViewSetNewsContent = findViewById(R.id.view_me_set_news_content);
        mBtnSaveNewsConent = findViewById(R.id.btn_me_save_news_detail);
        mTxtNewsDetail = findViewById(R.id.txt_news_detail);
        mViewSetNewsTitle.enableEditContext(true);
        //mViewSetNewsContent.enableEditContext(true);
    }

    @Override
    public void initListener() {
        mViewSetNewsType.setOnClickSettingBarViewListener(new SettingBarView.OnClickSettingBarViewListener() {
            @Override
            public void onClick() {
                mPresenter.getListNewsType();
            }
        });
        mBtnSaveNewsConent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mNewsType == null || TextUtils.isEmpty(mViewSetNewsType.getContent())){
                    ToastUtil.showToast("请选择新闻类型");
                    return;
                }
                if(TextUtils.isEmpty(mViewSetNewsTitle.getContent())){
                    ToastUtil.showToast("请输入新闻标题");
                    return;
                }
                if(TextUtils.isEmpty(mTxtNewsDetail.getText().toString())){
                    ToastUtil.showToast("请输入新闻内容");
                    return;
                }
                mPresenter.addNewsDetail(mNewsType.getId(),mViewSetNewsTitle.getContent(),mTxtNewsDetail.getText().toString());
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void injectPresenter() {
        DaggerNewsDetailAddComponent.builder().newsDetailAddModule(new NewsDetailAddModule(this)).build().inject(this);
    }

    @Override
    public void showNewsType(List<NewsType> typeList) {
        NewsTypeBottomSelectDialog newsTypeBottomSelectDialog = NewsTypeBottomSelectDialog.newInstance(typeList);
        newsTypeBottomSelectDialog.setItemClickListener(new BaseAdapter.OnItemClickListener<NewsType>() {
            @Override
            public void onItemClick(NewsType newsType, int position) {
                mNewsType = newsType;
                mViewSetNewsType.setContent(newsType.getTypename());
            }
        });
        newsTypeBottomSelectDialog.show(getSupportFragmentManager(),"dialog");
    }
}
