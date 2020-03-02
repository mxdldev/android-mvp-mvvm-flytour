package com.fly.tour.news.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.fly.tour.common.provider.INewsProvider
import com.fly.tour.news.fragment.MainNewsFragment

/**
 * Description: <NewsProvder><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
/**
 * Description: <NewsProvder><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Route(path = "/news/main", name = "新闻")
class NewsProvder : INewsProvider {
    override val mainNewsFragment: Fragment
        get() = MainNewsFragment.newInstance()

    override fun init(context: Context?) {
    }
}