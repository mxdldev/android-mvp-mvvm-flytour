package com.fly.tour.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: <RecyclerView空白区域点击监听><br>
 * Author:      gxl<br>
 * Date:        2018/8/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TouchyRecyclerView extends RecyclerView {
    private OnNoChildClickListener listener;

    public TouchyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnNoChildClickListener {
        public void onNoChildClick();
    }

    public void setOnNoChildClickListener(OnNoChildClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN
                && findChildViewUnder(event.getX(), event.getY()) == null) {
            if (listener != null) {
                listener.onNoChildClick();
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
