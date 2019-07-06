package com.fly.tour.news;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.fly.tour.api.news.NewsDetail;
import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.common.event.KeyCode;
import com.fly.tour.news.mvvm.factory.NewsViewModelFactory;
import com.fly.tour.news.mvvm.viewmodel.NewsDetailViewModel;

public class NewsDetailActivity extends BaseMvvmActivity<NewsDetailViewModel>{
    public static void startNewsDetailActivity(Context context,int id){
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KeyCode.News.NEWS_ID,id);
        context.startActivity(intent);
    }
    private TextView mTxtNewsTitle;
    private TextView mTxtNewsContent;

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView() {
        mTxtNewsTitle = findViewById(R.id.txt_news_detail_title);
        mTxtNewsContent = findViewById(R.id.txt_news_detail_content);
    }

    @Override
    public void initData() {
        int newsid = getIntent().getIntExtra(KeyCode.News.NEWS_ID,-1);
        mViewModel.getNewsDetailById(newsid);
    }

    public void showNewsDetail(NewsDetail newsDetail) {
        mTxtNewsTitle.setText(newsDetail.getTitle());
        mTxtNewsContent.setText(newsDetail.getContent());
    }

    @Override
    public Class<NewsDetailViewModel> onBindViewModel() {
        return NewsDetailViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return NewsViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {
        mViewModel.getNewsDetailSingleLiveEvent().observe(this, new Observer<NewsDetail>() {
            @Override
            public void onChanged(@Nullable NewsDetail newsDetail) {
                showNewsDetail(newsDetail);
            }
        });
    }
}
