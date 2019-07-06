package com.refresh.lib;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lib.R;
import com.refresh.lib.contract.PullContract;

/**
<<<<<<< HEAD
 * Description: <BaseRefreshLayout><br>
 * Author:      mxdl<br>
 * Date:        2019/02/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
=======
 * Description: <DaisyHeaderView><br>
 * Author: mxdl<br>
 * Date: 2019/2/25<br>
 * Version: V1.0.0<br>
 * Update: <br>
>>>>>>> 4.1.0
 */
public class DaisyHeaderView extends RelativeLayout implements PullContract {

  private TextView mTxtLoading;
  private ImageView mImgDaisy;
  private ObjectAnimator mRotation;

  public DaisyHeaderView(Context context) {
    this(context, null);
  }

  public DaisyHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(context);
  }

  public void initView(Context context) {
    LayoutInflater.from(context).inflate(R.layout.layout_daisy, this);
    mTxtLoading = findViewById(R.id.txt_loading);
    mTxtLoading.setText("下拉刷新");
    mImgDaisy = findViewById(R.id.img_daisy);
    mRotation = ObjectAnimator.ofFloat(mImgDaisy, "rotation", 0, 360).setDuration(800);
    mRotation.setRepeatCount(ValueAnimator.INFINITE);
    mRotation.setInterpolator(new LinearInterpolator());

  }

  @Override
  public void onPullEnable(boolean enable) {
    mTxtLoading.setText(enable ? "松开刷新" : "下拉刷新");
  }

  @Override
  public void onRefresh() {
    mTxtLoading.setText("正在刷新");
    mRotation.start();
  }

  public void setRefreshing(boolean b) {
    if (b) {
      mRotation.start();
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        mRotation.pause();
      }
    }
  }
}
