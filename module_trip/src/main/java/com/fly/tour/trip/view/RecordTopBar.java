package com.fly.tour.trip.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.tour.trip.R;


/**
 * Description: <RecordTopBar><br>
 * Author:      mxdl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RecordTopBar extends LinearLayout {

    private TextView mTxtDistance;
    private TextView mTxtTime;
    private Button mBtnHistory;
    private OnClickListener mListener;

    public RecordTopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_record_topbar,this);
        mTxtDistance = findViewById(R.id.txt_distance);
        mTxtTime = findViewById(R.id.txt_time);
        mBtnHistory = findViewById(R.id.btn_history);
        mBtnHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(view);
                }
            }
        });
    }

    public void setDistanceAndTime(String distance, String time) {
        setTxtDistance(distance);
        setTxtTime(time);
    }

    public void setTxtDistance(String distance) {
        if (mTxtDistance != null && !TextUtils.isEmpty(distance)) {
            mTxtDistance.setText(distance);
        }
    }

    public void setTxtTime(String time) {
        if (mTxtTime != null && !TextUtils.isEmpty(time)) {
            mTxtTime.setText(time);
        }
    }

    public void setHistoryBtnClickListener(OnClickListener listener) {
        mListener = listener;
    }
}
