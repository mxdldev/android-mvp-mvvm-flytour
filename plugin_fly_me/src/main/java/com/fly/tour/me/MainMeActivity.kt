package com.fly.tour.me

import com.fly.tour.common.base.BaseActivity
import com.fly.tour.me.fragment.MainMeFragment

class MainMeActivity : BaseActivity() {
    override fun onBindLayout(): Int {
        return R.layout.activity_me_main
    }

    override fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_content, MainMeFragment.Companion.newInstance()).commit()
    }
}