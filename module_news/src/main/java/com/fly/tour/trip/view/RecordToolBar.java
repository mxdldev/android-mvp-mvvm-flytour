package com.fly.tour.trip.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.fly.tour.trip.R;

/**
 * Description: <RecordToolBar><br>
 * Author:      gxl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RecordToolBar extends RelativeLayout implements View.OnClickListener {

    private CheckBox mBtnPause;//暂停、开始交换按钮
    private ImageButton mBtnSelectPhoto;
    private ImageButton mBtnVideo;
    private ImageButton mBtnTakePhoto;
    private ImageButton mBtnStop;
    private RecordToolBarClickListener mListener;

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_stop) {
            if (mListener != null) {
                mListener.onStopClick(view);
            }

        } else if (i == R.id.btn_selectphoto) {
            if (mListener != null) {
                mListener.onSelectPhotoClick(view);
            }

        } else if (i == R.id.btn_takephoto) {
            if (mListener != null) {
                mListener.onTakePhotoClick(view);
            }

        } else if (i == R.id.btn_video) {
            if (mListener != null) {
                mListener.onVideoClick(view);
            }

        }
    }

    public interface RecordToolBarClickListener {
        void onPauseClick(View view, boolean checked);

        void onStopClick(View view);

        void onVideoClick(View view);

        void onSelectPhotoClick(View view);

        void onTakePhotoClick(View view);
    }

    public RecordToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_record_toolbar, this);
        mBtnPause = findViewById(R.id.btn_pause);
        mBtnSelectPhoto = findViewById(R.id.btn_selectphoto);
        mBtnVideo = findViewById(R.id.btn_video);
        mBtnTakePhoto = findViewById(R.id.btn_takephoto);
        mBtnStop = findViewById(R.id.btn_stop);
        mBtnSelectPhoto.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
        mBtnTakePhoto.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mListener != null) {
                    mListener.onPauseClick(compoundButton, b);
                }
            }
        });
    }

    public void setListener(RecordToolBarClickListener listener) {
        mListener = listener;
    }
}