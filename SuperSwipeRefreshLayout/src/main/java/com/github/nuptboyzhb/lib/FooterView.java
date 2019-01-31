package com.github.nuptboyzhb.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jni.superswiperefreshlayout.R;

/**
 * Created by gxl on 2018/7/8.
 */

public class FooterView extends RelativeLayout {
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    public FooterView(@NonNull Context context) {
        this(context,null);
    }

    public FooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public void initView(Context context){
        View footerView = LayoutInflater.from(context)
                .inflate(R.layout.layout_footer, this);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
    }
    public void onPushEnable(boolean enable) {
        footerProgressBar.setVisibility(View.GONE);
        footerTextView.setText(enable ? "松开加载" : "上拉加载");
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setRotation(enable ? 0 : 180);
    }
    public void onLoadMore() {
        footerTextView.setText("正在加载...");
        footerImageView.setVisibility(View.GONE);
        footerProgressBar.setVisibility(View.VISIBLE);
    }
}
