package com.fly.tour.me.fragment

import com.fly.tour.common.base.BaseFragment
import com.fly.tour.common.view.SettingBarView.OnClickSettingBarViewListener
import com.fly.tour.me.NewsDetailAddActivity
import com.fly.tour.me.NewsTypeListActivity
import com.fly.tour.me.R
import kotlinx.android.synthetic.main.fragment_me_main.*

/**
 * Description: <MainMeFragment><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class MainMeFragment : BaseFragment() {
     override fun onBindLayout(): Int {
        return R.layout.fragment_me_main
    }

    override fun initListener() {
        view_setting_news_type.setOnClickSettingBarViewListener(object :
            OnClickSettingBarViewListener {
            override fun onClick() {
                NewsTypeListActivity.startNewsTypeListActivity(mActivity)
            }
        })
        view_setting_news_detail.setOnClickSettingBarViewListener(object :
            OnClickSettingBarViewListener {
            override fun onClick() {
                NewsDetailAddActivity.startNewsDetailAddActivity(mActivity)
            }

        })
    }

    override fun initData() {

    }

    companion object {
        fun newInstance(): MainMeFragment {
            return MainMeFragment()
        }
    }
}