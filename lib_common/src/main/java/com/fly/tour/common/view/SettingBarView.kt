package com.fly.tour.common.view

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.fly.tour.common.R

/**
 * Description: <SettingBarView><br>
 * Author:      mxdl<br>
 * Date:        2019/3/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</SettingBarView> */
class SettingBarView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private val imgLeftIcon: ImageView?
    private val txtSetTitle: TextView
    private val txtSetContent: EditText?
    private val imgRightIcon: ImageView
    private val layoutSettingBar: RelativeLayout
    private var mOnClickSettingBarViewListener: OnClickSettingBarViewListener? = null
    private var mOnClickRightImgListener: OnClickRightImgListener? = null
    private var isEdit = false//是否需要编辑

    var content: String?
        get() = txtSetContent?.text?.toString()
        set(value) {
            if (txtSetContent != null && !TextUtils.isEmpty(value)) {
                txtSetContent.setText(value)
            }
        }

    interface OnClickSettingBarViewListener {
        fun onClick()
    }

    interface OnClickRightImgListener {
        fun onClick()
    }

    fun setOnClickRightImgListener(onClickRightImgListener: OnClickRightImgListener) {
        mOnClickRightImgListener = onClickRightImgListener
    }

    fun setOnClickSettingBarViewListener(onClickSettingBarViewListener: OnClickSettingBarViewListener) {
        mOnClickSettingBarViewListener = onClickSettingBarViewListener
    }

    init {
        View.inflate(context, R.layout.view_setting_bar, this)
        layoutSettingBar = findViewById(R.id.layout_setting_bar)
        imgLeftIcon = findViewById(R.id.img_left_icon)
        txtSetTitle = findViewById(R.id.txt_set_title)
        txtSetContent = findViewById(R.id.txt_set_content)
        imgRightIcon = findViewById(R.id.img_right_icon)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingBarView)
        val isVisableLeftIcon = typedArray.getBoolean(R.styleable.SettingBarView_set_left_icon_visable, false)
        val isVisableRightIcon = typedArray.getBoolean(R.styleable.SettingBarView_set_right_icon_visable, false)
        val title = typedArray.getString(R.styleable.SettingBarView_set_title)
        val hint = typedArray.getString(R.styleable.SettingBarView_set_content_hint)
        val content = typedArray.getString(R.styleable.SettingBarView_set_content)
        val rightIcon = typedArray.getResourceId(R.styleable.SettingBarView_set_right_icon, 0)
        val leftIcon = typedArray.getResourceId(R.styleable.SettingBarView_set_left_icon, 0)

        imgLeftIcon!!.visibility = if (isVisableLeftIcon) View.VISIBLE else View.GONE
        txtSetTitle.text = title
        txtSetContent!!.hint = hint
        txtSetContent.setText(content)
        imgRightIcon.visibility = if (isVisableRightIcon) View.VISIBLE else View.GONE
        if (leftIcon > 0) {
            imgLeftIcon.setImageResource(leftIcon)
        }
        if (rightIcon > 0) {
            imgRightIcon.setImageResource(rightIcon)
        }
        imgRightIcon.setOnClickListener {
            if (mOnClickRightImgListener != null) {
                mOnClickRightImgListener!!.onClick()
            }
        }
        layoutSettingBar.setOnClickListener {
            if (mOnClickSettingBarViewListener != null) {
                mOnClickSettingBarViewListener!!.onClick()
            }
        }
    }

    fun enableEditContext(b: Boolean) {
        isEdit = b
        if (txtSetContent != null) {
            txtSetContent.isEnabled = b
        }
    }

    fun showImgLeftIcon(show: Boolean) {
        if (imgLeftIcon != null) {
            imgLeftIcon.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return !isEdit
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return layoutSettingBar.onTouchEvent(event)
    }

}
