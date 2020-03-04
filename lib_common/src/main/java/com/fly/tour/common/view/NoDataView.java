package com.fly.tour.common.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fly.tour.common.R;

/**
 * Description: <NoDataView><br>
 * Author:      mxdl<br>
 * Date:        2019/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NoDataView extends RelativeLayout {

    private final RelativeLayout mRlNoDataRoot;
    private final ImageView mImgNoDataView;

    public NoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_no_data,this);
        mRlNoDataRoot = findViewById(R.id.rl_no_data_root);
        mImgNoDataView = findViewById(R.id.img_no_data);
    }

    public void setNoDataBackground(@ColorRes int  colorResId){
        mRlNoDataRoot.setBackgroundColor(getResources().getColor(colorResId));
    }

    public void setNoDataView(@DrawableRes int imgResId){
        mImgNoDataView.setImageResource(imgResId);
    }
}