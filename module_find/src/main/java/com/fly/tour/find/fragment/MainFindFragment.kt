package com.fly.tour.find.fragment

import android.view.View
import com.fly.tour.common.base.BaseFragment
import com.fly.tour.find.R

/**
 * Description: <MainFindFragment><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class MainFindFragment: BaseFragment() {
    override fun onBindLayout(): Int {
        return R.layout.fragment_find_main
    }

    override fun initData() {
    }
    companion object{
        fun newsInstance():MainFindFragment{
            return MainFindFragment()
        }
    }
}