package com.refresh.lib

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.lib.R

import com.refresh.lib.contract.PushContract

/**
 * Description: <DaisyHeaderView><br>
 * Author: mxdl<br>
 * Date: 2019/2/25<br>
 * Version: V1.0.0<br>
 * Update: <br>
</DaisyHeaderView> */
class DaisyFooterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs), PushContract {

    private lateinit var mTxtLoading: TextView
    private lateinit var mImgDaisy: ImageView
    private lateinit var mRotation: ObjectAnimator

    init {
        initView(context)
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_daisy, this)
        mTxtLoading = findViewById(R.id.txt_loading)
        mTxtLoading.text = "上拉加载更多..."
        mImgDaisy = findViewById(R.id.img_daisy)
        mRotation = ObjectAnimator.ofFloat(mImgDaisy, "rotation", 0f, 360f).setDuration(800)
        mRotation.repeatCount = ValueAnimator.INFINITE
        mRotation.interpolator = LinearInterpolator()

    }

    override fun onPushEnable(enable: Boolean) {
        mTxtLoading.text = if (enable) "松开加载" else "上拉加载"
    }

    override fun onLoadMore() {
        mTxtLoading.text = "正在加载..."
        mRotation.start()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setLoadMore(b: Boolean) {
        if (b) {
            mRotation.start()
        } else {
            mRotation.pause()
        }
    }
}
