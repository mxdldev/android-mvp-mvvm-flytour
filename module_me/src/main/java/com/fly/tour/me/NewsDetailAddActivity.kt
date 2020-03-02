package com.fly.tour.me

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.fly.tour.common.base.BaseAdapter
import com.fly.tour.common.base.BaseMvpActivity
import com.fly.tour.common.util.ToastUtil
import com.fly.tour.common.view.SettingBarView.OnClickSettingBarViewListener
import com.fly.tour.db.entity.NewsType
import com.fly.tour.me.contract.NewsDetailAddContract
import com.fly.tour.me.model.NewsDetailAddModel
import com.fly.tour.me.presenter.NewsDetailAddPresenter
import com.fly.tour.me.view.NewsTypeBottomSelectDialog
import kotlinx.android.synthetic.main.activity_news_detail_add.*
/**
 * Description: <NewsDetailAddActivity><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsDetailAddActivity :
    BaseMvpActivity<NewsDetailAddModel, NewsDetailAddContract.View, NewsDetailAddPresenter>(),
    NewsDetailAddContract.View {
    private var mNewsType: NewsType? = null

    override fun initPresenter(): NewsDetailAddPresenter {
        return NewsDetailAddPresenter(this)
    }

    override fun onBindLayout(): Int {
        return R.layout.activity_news_detail_add
    }

    override fun initListener() {
        mViewMeSetNewsTitle.enableEditContext(true)
        mViewMeSetNewsType.setOnClickSettingBarViewListener(object :
            OnClickSettingBarViewListener {
            override fun onClick() {
                val newsTypeBottomSelectDialog = NewsTypeBottomSelectDialog.newInstance()
                newsTypeBottomSelectDialog.setItemClickListener(object :
                    BaseAdapter.OnItemClickListener<NewsType> {
                    override fun onItemClick(newsType: NewsType, position: Int) {
                        mNewsType = newsType
                        mViewMeSetNewsType.content = newsType.typename
                    }
                })
                newsTypeBottomSelectDialog.show(supportFragmentManager, "dialog")
            }
        })
        mBtnMeSaveNewsDetail.setOnClickListener {
            if (mNewsType == null || TextUtils.isEmpty(mViewMeSetNewsType.content)) {
                ToastUtil.showToast("请选择新闻类型")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(mViewMeSetNewsTitle.content)) {
                ToastUtil.showToast("请输入新闻标题")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(mTxtNewsDetail.text.toString())) {
                ToastUtil.showToast("请输入新闻内容")
                return@setOnClickListener
            }
            mPresenter?.addNewsDetail(
                mNewsType!!.id,
                mViewMeSetNewsTitle.content!!,
                mTxtNewsDetail.text.toString()
            )
        }
    }

    override fun initData() {
    }

    companion object {
        fun startNewsDetailAddActivity(context: Context) {
            context.startActivity(Intent(context, NewsDetailAddActivity::class.java))
        }
    }

    override fun getTootBarTitle(): String {
        return super.getTootBarTitle()
    }


    override fun onBindToolbarLayout(): Int {
        return super.onBindToolbarLayout()
    }
}
