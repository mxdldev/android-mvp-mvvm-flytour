package com.fly.tour.find.fragment

import android.view.View
import com.fly.tour.common.base.BaseFragment
import com.fly.tour.find.R

/**
 * Description: <发现Fragment><br></br>
 * Author:      mxdl<br></br>
 * Date:        2018/12/11<br></br>
 * Version:     V1.0.0<br></br>
 * Update:     <br></br>
</发现Fragment> */
class MainFindFragment : BaseFragment() {
    override fun onBindLayout(): Int {
        return R.layout.fragment_find_main
    }

    fun initView(view: View?) {}
    override fun initData() {}
    val toolbarTitle: String?
        get() = null

    companion object {
        val TAG = MainFindFragment::class.java.simpleName
        fun newInstance(): MainFindFragment {
            return MainFindFragment()
        }
    }
}