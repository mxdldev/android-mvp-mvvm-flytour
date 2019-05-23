package com.fly.tour.common.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.fly.tour.common.R;

/**
 * Description: <LoadingView><br>
 * Author:      gxl<br>
 * Date:        2019/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class LoadingTransView extends RelativeLayout {
    private final AnimationDrawable mAnimationDrawable;
    public LoadingTransView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_trans_loading,this);
        ImageView imgLoading = findViewById(R.id.img_trans_loading);
        mAnimationDrawable = (AnimationDrawable) imgLoading.getDrawable();
    }
    public void startLoading(){
        mAnimationDrawable.start();
    }
    public void stopLoading(){
        mAnimationDrawable.stop();
    }
    public void loading(boolean b){
        if(b){
            startLoading();
        }else{
            stopLoading();
        }
    }
}
