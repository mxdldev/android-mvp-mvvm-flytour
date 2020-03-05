package com.fly.tour.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView

/**
 * Description: <文字图标居中TextView><br>
 * Author:      mxdl<br>
 * Date:        2018/8/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</文字图标居中TextView> */
@SuppressLint("AppCompatCustomView")
class DrawableCenterTextView : TextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    override fun onDraw(canvas: Canvas) {
        val drawables = compoundDrawables
        if (drawables != null) {
            val drawableLeft = drawables[0]
            if (drawableLeft != null) {
                val textWidth = paint.measureText(text.toString())
                val drawablePadding = compoundDrawablePadding
                var drawableWidth = 0
                drawableWidth = drawableLeft.intrinsicWidth
                val bodyWidth = textWidth + drawableWidth.toFloat() + drawablePadding.toFloat()
                val v = width - bodyWidth
                if (v > 0)
                    canvas.translate(v / 2, 0f)
                else
                    canvas.translate(0f, 0f)
            }
        }
        super.onDraw(canvas)
    }
}