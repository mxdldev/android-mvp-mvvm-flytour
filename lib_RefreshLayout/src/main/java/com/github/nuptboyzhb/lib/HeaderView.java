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

/**
 * Created by gxl on 2018/7/8.
 */

public class HeaderView extends RelativeLayout {
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    public HeaderView(@NonNull Context context) {
        this(context,null);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(@NonNull Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_head,this);
        progressBar = (ProgressBar) findViewById(R.id.pb_view);
        textView = (TextView) findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
    }

    public void onPullEnable(boolean enable){
        progressBar.setVisibility(View.GONE);
        textView.setText(enable ? "松开刷新" : "下拉刷新");
        imageView.setVisibility(View.VISIBLE);
        imageView.setRotation(enable ? 180 : 0);
    }
    public void onRefresh(){
        textView.setText("正在刷新");
        imageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
