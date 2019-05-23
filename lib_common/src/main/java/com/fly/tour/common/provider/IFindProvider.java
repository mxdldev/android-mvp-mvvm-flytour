package com.fly.tour.common.provider;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Description: <IFindProvider><br>
 * Author:      gxl<br>
 * Date:        2019/5/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface IFindProvider extends IProvider {
    Fragment getMainFindFragment();
}
