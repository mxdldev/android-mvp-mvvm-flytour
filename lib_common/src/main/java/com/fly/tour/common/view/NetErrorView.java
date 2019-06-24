package com.fly.tour.common.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.fly.tour.common.R;
/**
 * Description: <LoadingView><br>
 * Author:      mxdl<br>
 * Date:        2019/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NetErrorView extends RelativeLayout {
    private OnClickListener mOnClickListener;
    private final RelativeLayout mRlNetWorkError;

    public NetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_net_error,this);
        findViewById(R.id.btn_net_refresh).setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onClick(v);
                }
            }
        });
        mRlNetWorkError = findViewById(R.id.rl_net_error_root);
    }
    public void setRefreshBtnClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }

    public void setNetErrorBackground(@ColorRes int  colorResId){
        mRlNetWorkError.setBackgroundColor(getResources().getColor(colorResId));
    }
}
