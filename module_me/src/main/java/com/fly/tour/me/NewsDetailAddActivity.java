package com.fly.tour.me;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.base.BaseAdapter;
import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.mvvm.factory.MeViewModelFactory;
import com.fly.tour.me.mvvm.model.NewsDetailAddViewModel;
import com.fly.tour.me.view.NewsTypeBottomSelectDialog;

import java.util.List;

public class NewsDetailAddActivity extends BaseMvvmActivity<NewsDetailAddViewModel> {

    private SettingBarView mViewSetNewsType;
    private SettingBarView mViewSetNewsTitle;
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
        mBtnSaveNewsConent = findViewById(R.id.btn_me_save_news_detail);
        mTxtNewsDetail = findViewById(R.id.txt_news_detail);
        mViewSetNewsTitle.enableEditContext(true);
    }

    @Override
    public void initListener() {
        mViewSetNewsType.setOnClickSettingBarViewListener(new SettingBarView.OnClickSettingBarViewListener() {
            @Override
            public void onClick() {
                mViewModel.getListNewsType();
            }
        });
        mBtnSaveNewsConent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mNewsType == null || TextUtils.isEmpty(mViewSetNewsType.getContent())) {
                    ToastUtil.showToast("请选择新闻类型");
                    return;
                }
                if (TextUtils.isEmpty(mViewSetNewsTitle.getContent())) {
                    ToastUtil.showToast("请输入新闻标题");
                    return;
                }
                if (TextUtils.isEmpty(mTxtNewsDetail.getText().toString())) {
                    ToastUtil.showToast("请输入新闻内容");
                    return;
                }
                mViewModel.addNewsDetail(mNewsType.getId(), mViewSetNewsTitle.getContent(), mTxtNewsDetail.getText().toString());
            }
        });
    }

    @Override
    public void initData() {
    }

    public void showNewsType(List<NewsType> typeList) {
        NewsTypeBottomSelectDialog newsTypeBottomSelectDialog = NewsTypeBottomSelectDialog.newInstance(typeList);
        newsTypeBottomSelectDialog.setItemClickListener(new BaseAdapter.OnItemClickListener<NewsType>() {
            @Override
            public void onItemClick(NewsType newsType, int position) {
                mNewsType = newsType;
                mViewSetNewsType.setContent(newsType.getTypename());
            }
        });
        newsTypeBottomSelectDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public Class<NewsDetailAddViewModel> onBindViewModel() {
        return NewsDetailAddViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return MeViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {
        mViewModel.getSingleNewsTypeLiveEvent().observe(this, new Observer<List<NewsType>>() {
            @Override
            public void onChanged(@Nullable List<NewsType> newsTypes) {
                showNewsType(newsTypes);
            }
        });
    }
}
