package com.fly.tour.me

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.fly.tour.common.base.BaseMvpActivity
import com.fly.tour.common.event.EventCode
import com.fly.tour.common.event.me.NewsTypeCrudEvent
import com.fly.tour.common.util.ToastUtil
import com.fly.tour.me.contract.NewsTypeAddContract
import com.fly.tour.me.model.NewsTypeAddModel
import com.fly.tour.me.presenter.NewsTypeAddPresenter
import kotlinx.android.synthetic.main.activity_news_type_add.*
import org.greenrobot.eventbus.EventBus
/**
 * Description: <NewsTypeAddActivity><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeAddActivity :
    BaseMvpActivity<NewsTypeAddModel, NewsTypeAddContract.View, NewsTypeAddPresenter>(),
    NewsTypeAddContract.View {
    override fun initPresenter(): NewsTypeAddPresenter {
        return NewsTypeAddPresenter(this)
    }

    override fun initListener() {
        mViewMeNewsType.enableEditContext(true)
        mBtnMeNewsType.setOnClickListener {
            var content = mViewMeNewsType.content
            if(TextUtils.isEmpty(content)){
                ToastUtil.showToast("请输入新闻类型")
                return@setOnClickListener
            }
            mPresenter?.addNewsType(content!!)
        }
    }

    override fun onBindLayout(): Int {
        return R.layout.activity_news_type_add
    }

    override fun initData() {
    }

    override fun finishActivity() {
        EventBus.getDefault().post(NewsTypeCrudEvent<Any>(EventCode.MeCode.NEWS_TYPE_ADD))
        setResult(Activity.RESULT_OK, Intent())
        super.finishActivity()
    }
}
