package com.fly.tour.common.view

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Description: <RecyclerView空白区域点击监听><br>
 * Author:      mxdl<br>
 * Date:        2018/8/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class TouchyRecyclerView(context: Context, attrs: AttributeSet) : androidx.recyclerview.widget.RecyclerView(context, attrs) {
    private var listener: OnNoChildClickListener? = null

    interface OnNoChildClickListener {
        fun onNoChildClick()
    }

    fun setOnNoChildClickListener(listener: OnNoChildClickListener) {
        this.listener = listener
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && findChildViewUnder(event.x, event.y) == null) {
            if (listener != null) {
                listener!!.onNoChildClick()
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
