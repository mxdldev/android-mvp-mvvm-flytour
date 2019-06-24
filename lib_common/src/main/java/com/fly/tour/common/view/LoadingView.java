package com.fly.tour.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fly.tour.common.R;


/**
 * Description: <><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class LoadingView extends RelativeLayout {

  private ImageView img_wheel1;
  private AnimationDrawable mAnimation;

  public LoadingView(Context context) {
    this(context, null);
  }

  public LoadingView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LoadingView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initView(context);
  }

  @SuppressLint("ResourceType")
  public void onLoading() {
    setVisibility(VISIBLE);
    img_wheel1.setBackgroundResource(R.anim.loading);
    mAnimation = (AnimationDrawable) img_wheel1.getBackground();
    mAnimation.start();

  }

  public void endLoading() {
    mAnimation.stop();
  }

  private void initView(Context context) {
    View view = View.inflate(context, R.layout.base_loading, this);
    img_wheel1 = (ImageView) view.findViewById(R.id.img_wheel1);
  }

}
