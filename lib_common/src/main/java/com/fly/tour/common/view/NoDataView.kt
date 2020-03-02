package com.fly.tour.common.view

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import com.fly.tour.common.R

/**
 * Description: <NoDataView><br>
 * Author:      mxdl<br>
 * Date:        2019/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</NoDataView> */
class NoDataView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private val mRlNoDataRoot: RelativeLayout
    private val mImgNoDataView: ImageView

    init {
        View.inflate(context, R.layout.view_no_data, this)
        mRlNoDataRoot = findViewById(R.id.rl_no_data_root)
        mImgNoDataView = findViewById(R.id.img_no_data)
    }

    fun setNoDataBackground(@ColorRes colorResId: Int) {
        mRlNoDataRoot.setBackgroundColor(resources.getColor(colorResId))
    }

    fun setNoDataView(@DrawableRes imgResId: Int) {
        mImgNoDataView.setImageResource(imgResId)
    }
}