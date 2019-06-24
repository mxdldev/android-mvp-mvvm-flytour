package com.fly.tour.news;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.fly.tour.common.base.BaseMvpActivity;
import com.fly.tour.common.event.KeyCode;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsDetailContract;
import com.fly.tour.news.model.NewsDetailModel;
import com.fly.tour.news.presenter.NewsDetailPresenter;
import com.fly.tour.trip.R;
/**
 * Description: <NewsDetailActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailActivity extends BaseMvpActivity<NewsDetailModel,NewsDetailContract.View,NewsDetailPresenter> implements NewsDetailContract.View{

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
        mPresenter.getNewsDetailById(newsid);
    }

    @Override
    public NewsDetailPresenter initPresenter() {
        return new NewsDetailPresenter(this);
    }

    @Override
    public void showNewsDetail(NewsDetail newsDetail) {
        mTxtNewsTitle.setText(newsDetail.getTitle());
        mTxtNewsContent.setText(newsDetail.getContent());
    }
}
