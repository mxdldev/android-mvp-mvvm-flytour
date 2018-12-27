package com.fly.tour.common.adapter;

/**
 * Description: <><br>
 * Author:      gxl<br>
 * Date:        2018/6/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description: <BaseViewPagerAdapter></br>
 * Author:      gxl</br>
 * Date:        2018/3/9</br>
 * Version:     V1.0.0</br>
 * Update:     </br>
 */
public class BaseViewPagerAdapter<T> extends PagerAdapter {
    protected Context mContext;
    protected List<T> list;

    public BaseViewPagerAdapter(Context context, List<T> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
