package com.fly.tour.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fly.tour.common.R;

/**
 * Description: <SettingBarView><br>
 * Author:      gxl<br>
 * Date:        2019/3/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class SettingBarView extends RelativeLayout {
    private ImageView imgLeftIcon;
    private TextView txtSetTitle;
    private EditText txtSetContent;
    private ImageView imgRightIcon;
    private RelativeLayout layoutSettingBar;
    private OnClickSettingBarViewListener mOnClickSettingBarViewListener;
    private OnClickRightImgListener mOnClickRightImgListener;
    private boolean isEdit = false;//是否需要编辑

    public interface OnClickSettingBarViewListener {
        void onClick();
    }

    public interface OnClickRightImgListener {
        void onClick();
    }

    public void setOnClickRightImgListener(OnClickRightImgListener onClickRightImgListener) {
        mOnClickRightImgListener = onClickRightImgListener;
    }

    public void setOnClickSettingBarViewListener(OnClickSettingBarViewListener onClickSettingBarViewListener) {
        mOnClickSettingBarViewListener = onClickSettingBarViewListener;
    }

    public SettingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_setting_bar, this);
        layoutSettingBar = findViewById(R.id.layout_setting_bar);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        txtSetTitle = findViewById(R.id.txt_set_title);
        txtSetContent = findViewById(R.id.txt_set_content);
        imgRightIcon = findViewById(R.id.img_right_icon);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingBarView);
        boolean isVisableLeftIcon = typedArray.getBoolean(R.styleable.SettingBarView_set_left_icon_visable, false);
        boolean isVisableRightIcon = typedArray.getBoolean(R.styleable.SettingBarView_set_right_icon_visable, false);
        String title = typedArray.getString(R.styleable.SettingBarView_set_title);
        String hint = typedArray.getString(R.styleable.SettingBarView_set_content_hint);
        String content = typedArray.getString(R.styleable.SettingBarView_set_content);
        int rightIcon = typedArray.getResourceId(R.styleable.SettingBarView_set_right_icon, 0);
        int leftIcon = typedArray.getResourceId(R.styleable.SettingBarView_set_left_icon, 0);

        imgLeftIcon.setVisibility(isVisableLeftIcon ? View.VISIBLE : View.GONE);
        txtSetTitle.setText(title);
        txtSetContent.setHint(hint);
        txtSetContent.setText(content);
        imgRightIcon.setVisibility(isVisableRightIcon ? View.VISIBLE : View.GONE);
        if (leftIcon > 0) {
            imgLeftIcon.setImageResource(leftIcon);
        }
        if (rightIcon > 0) {
            imgRightIcon.setImageResource(rightIcon);
        }
        imgRightIcon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnClickRightImgListener != null) {
                    mOnClickRightImgListener.onClick();
                }
            }
        });
        layoutSettingBar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnClickSettingBarViewListener != null) {
                    mOnClickSettingBarViewListener.onClick();
                }
            }
        });
    }

    public void setContent(String value) {
        if (txtSetContent != null && !TextUtils.isEmpty(value)) {
            txtSetContent.setText(value);
        }
    }

    public String getContent() {
        if (txtSetContent != null) {
            return txtSetContent.getText().toString();
        }
        return null;
    }

    public void enableEditContext(boolean b) {
        isEdit = b;
        if (txtSetContent != null) {
            txtSetContent.setEnabled(b);
        }
    }

    public void showImgLeftIcon(boolean show) {
        if (imgLeftIcon != null) {
            imgLeftIcon.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isEdit;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return layoutSettingBar.onTouchEvent(event);
    }

}
