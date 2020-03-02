package com.fly.tour.find.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.fly.tour.common.provider.IFindProvider
import com.fly.tour.find.fragment.MainFindFragment

/**
 * Description: <FindProvider><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Route(path = "/find/main", name = "发现服务")
class FindProvider : IFindProvider {
    override val mainFindFragment: Fragment
        get() = MainFindFragment.newsInstance()

    override fun init(context: Context?) {
    }
}