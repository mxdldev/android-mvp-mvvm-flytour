package com.fly.tour.news.provider;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fly.tour.common.provider.INewsProvider;
import com.fly.tour.news.fragment.MainNewsFragment;

/**
 * Description: <NewProvider><br>
 * Author:      gxl<br>
 * Date:        2019/5/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Route(path = "/news/main",name = "新闻")
public class NewProvider implements INewsProvider {
    @Override
    public Fragment getMainNewsFragment() {
        return MainNewsFragment.newInstance();
    }

    @Override
    public void init(Context context) {

    }
}
