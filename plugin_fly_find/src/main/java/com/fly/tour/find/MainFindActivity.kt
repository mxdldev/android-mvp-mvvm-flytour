package com.fly.tour.find

import com.fly.tour.common.base.BaseActivity
import com.fly.tour.find.fragment.MainFindFragment

class MainFindActivity : BaseActivity() {
    override fun onBindLayout(): Int {
        return R.layout.activity_find_main
    }

    override fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_content, MainFindFragment.Companion.newInstance()).commit()
    }
}