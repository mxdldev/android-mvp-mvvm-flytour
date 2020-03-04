package com.fly.tour.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Description: <文字图标居中TextView><br>
 * Author:      mxdl<br>
 * Date:        2018/8/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@SuppressLint("AppCompatCustomView")
public class DrawableCenterTextView extends TextView {

    public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                float v = getWidth() - bodyWidth;
                if (v > 0)
                    canvas.translate(v / 2, 0);
                else
                    canvas.translate(0, 0);
            }
        }
        super.onDraw(canvas);
    }
}