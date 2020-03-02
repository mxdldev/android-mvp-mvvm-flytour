package com.fly.tour.common.view

import android.content.Context
import androidx.annotation.ColorRes
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.fly.tour.common.R

/**
 * Description: <LoadingView><br>
 * Author:      mxdl<br>
 * Date:        2019/3/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</LoadingView> */
class NetErrorView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private var mOnClickListener: View.OnClickListener? = null
    private val mRlNetWorkError: RelativeLayout

    init {
        View.inflate(context, R.layout.view_net_error, this)
        findViewById<View>(R.id.btn_net_refresh).setOnClickListener { v ->
            if (mOnClickListener != null) {
                mOnClickListener!!.onClick(v)
            }
        }
        mRlNetWorkError = findViewById(R.id.rl_net_error_root)
    }

    fun setRefreshBtnClickListener(listener: View.OnClickListener) {
        mOnClickListener = listener
    }

    fun setNetErrorBackground(@ColorRes colorResId: Int) {
        mRlNetWorkError.setBackgroundColor(resources.getColor(colorResId))
    }
}
