package com.fly.tour.news

import com.fly.tour.common.base.BaseActivity
import com.fly.tour.news.fragment.MainNewsFragment

class MainNewsActivity : BaseActivity() {
    override fun onBindLayout(): Int {
        return R.layout.activity_news_main
    }

    override fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_content, MainNewsFragment.Companion.newInstance()).commit()
    }
}