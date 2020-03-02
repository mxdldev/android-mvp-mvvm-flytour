package com.fly.tour.news

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fly.tour.common.base.BaseMvpActivity
import com.fly.tour.common.event.KeyCode
import com.fly.tour.db.entity.NewsDetail
import com.fly.tour.news.contract.NewsDetailContract
import com.fly.tour.news.model.NewsDetailModel
import com.fly.tour.news.presenter.NewsDetailPresenter
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity :
    BaseMvpActivity<NewsDetailModel, NewsDetailContract.View, NewsDetailPresenter>(),
    NewsDetailContract.View {
    companion object {
        fun startNewsDetailActivity(context: Context, id: Int) {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(KeyCode.News.NEWS_ID, id)
            context.startActivity(intent)
        }
    }

    override fun showViewDetail(newsDetail: NewsDetail?) {
        mTxtNewsDetailTitle.text = newsDetail?.title
        mTxtNewsDetailContent.text = newsDetail?.content
    }

    override fun initPresenter(): NewsDetailPresenter {
        return NewsDetailPresenter(this)
    }

    override fun onBindLayout(): Int {
        return R.layout.activity_news_detail
    }

    override fun initData() {
        var newsid = intent.getIntExtra(KeyCode.News.NEWS_ID, -1)
        mPresenter?.getNewsDetailById(newsid)
    }

}
