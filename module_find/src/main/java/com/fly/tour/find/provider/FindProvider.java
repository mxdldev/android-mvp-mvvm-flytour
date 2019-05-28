package com.fly.tour.find.provider;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fly.tour.common.provider.IFindProvider;
import com.fly.tour.find.fragment.MainFindFragment;

/**
 * Description: <FindProvider><br>
 * Author:      gxl<br>
 * Date:        2019/5/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Route(path = "/find/main",name = "发现服务")
public class FindProvider implements IFindProvider {
    @Override
    public Fragment getMainFindFragment() {
        return MainFindFragment.newInstance();
    }

    @Override
    public void init(Context context) {

    }
}
