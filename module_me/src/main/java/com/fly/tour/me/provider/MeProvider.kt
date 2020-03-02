package com.fly.tour.me.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.fly.tour.common.provider.IMeProvider
import com.fly.tour.me.fragment.MainMeFragment

/**
 * Description: <MeProvider><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Route(path = "/me/main", name = "我的服务")
class MeProvider: IMeProvider {
    override val mainMeFragment: Fragment
        get() = MainMeFragment.newInstance()

    override fun init(context: Context?) {
    }
}