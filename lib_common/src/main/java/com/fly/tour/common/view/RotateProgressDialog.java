package com.fly.tour.common.view;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.tour.common.R;
import com.fly.tour.common.util.DisplayUtil;


/**
 * Description: <旋转进度的dialog><br>
 * Author: gxl<br>
 * Date: 2018/4/19<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class RotateProgressDialog extends DialogFragment {

  private TextView mTxtProgress;
  private ImageView mImgProgress;

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow().setLayout(DisplayUtil.dip2px(100), DisplayUtil.dip2px(100));
      dialog.setCancelable(false);
      dialog.setCanceledOnTouchOutside(false);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.rotate_progress_dialog_layout, null,false);
    mImgProgress = view.findViewById(R.id.img_progress);
    mTxtProgress = view.findViewById(R.id.txt_progress);

    //mImgProgress.animate().rotation(360).setDuration(5).r.start();
    ObjectAnimator rotation = ObjectAnimator.ofFloat(mImgProgress, "rotation", 0f, 359f);//最好是0f到359f，0f和360f的位置是重复的
    rotation.setRepeatCount(ObjectAnimator.INFINITE);
    rotation.setInterpolator(new LinearInterpolator());
    rotation.setDuration(2000);
    rotation.start();
    return view;
  }
  public void setProgress(int value){
    mTxtProgress.setText(value+"%");
  }
}
