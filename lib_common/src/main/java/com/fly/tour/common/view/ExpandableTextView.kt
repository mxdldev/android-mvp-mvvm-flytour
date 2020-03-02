/*
 * Copyright (C) 2011 The Android Open Source Project Copyright 2014 Manabu Shimobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.fly.tour.common.view

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import com.fly.tour.common.R


/**
 * 默认展开状态： ExpandableTextView.expandView(); ExpandableTextView.showToogleView(false);
 */
class ExpandableTextView : LinearLayout, View.OnClickListener {

    protected var mTv: TextView? = null

    protected lateinit var mToggleView: View // View to expand/collapse

    private var mRelayout: Boolean = false

    private var mCollapsed = true // Show short version as default.

    private var mCollapsedHeight: Int = 0

    private var mTextHeightWithMaxLines: Int = 0

    private var mMaxCollapsedLines: Int = 0

    private var mMarginBetweenTxtAndBottom: Int = 0

    private var mExpandIndicatorController: ExpandIndicatorController? = null

    private var mAnimationDuration: Int = 0

    private var mAnimAlphaStart: Float = 0.toFloat()

    private var mAnimating: Boolean = false

    @IdRes
    private var mExpandableTextId = R.id.expandable_text

    @IdRes
    private var mExpandCollapseToggleId = R.id.expand_collapse

    private var mExpandToggleOnTextClick: Boolean = false

    /* Listener for callback */
    private var mListener: OnExpandStateChangeListener? = null

    /* For saving collapsed status when used in ListView */
    private var mCollapsedStatus: SparseBooleanArray? = null
    private var mPosition: Int = 0

    var text: CharSequence?
        get() = if (mTv == null) {
            ""
        } else mTv!!.text
        set(text) {
            mRelayout = true
            mTv!!.text = text
            visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
            clearAnimation()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            requestLayout()
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        init(attrs)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    override fun setOrientation(orientation: Int) {
        kotlin.require(HORIZONTAL != orientation) { "ExpandableTextView only supports Vertical Orientation." }
        super.setOrientation(orientation)
    }

    override fun onClick(view: View) {
        if (mToggleView.visibility != View.VISIBLE) {
            return
        }

        mCollapsed = !mCollapsed
        mExpandIndicatorController!!.changeState(mCollapsed)

        if (mCollapsedStatus != null) {
            mCollapsedStatus!!.put(mPosition, mCollapsed)
        }

        // mark that the animation is in progress
        mAnimating = true

        val animation: Animation
        if (mCollapsed) {
            animation = ExpandCollapseAnimation(this, height, mCollapsedHeight)
        } else {
            animation = ExpandCollapseAnimation(this, height,
                    height + mTextHeightWithMaxLines - mTv!!.height)
        }

        animation.setFillAfter(true)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                applyAlphaAnimation(mTv, mAnimAlphaStart)
            }

            override fun onAnimationEnd(animation: Animation) {
                // clear animation here to avoid repeated applyTransformation() calls
                clearAnimation()
                // clear the animation flag
                mAnimating = false

                // notify the listener
                if (mListener != null) {
                    mListener!!.onExpandStateChanged(mTv, !mCollapsed)
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        clearAnimation()
        startAnimation(animation)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // while an animation is in progress, intercept all the touch events to children to
        // prevent extra clicks during the animation
        return mAnimating
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // If no change, measure and return
        if (!mRelayout || visibility == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        mRelayout = false

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mToggleView.visibility = View.GONE
        mTv!!.maxLines = Integer.MAX_VALUE

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // If the text fits in collapsed mode, we are done.
        if (mTv!!.lineCount <= mMaxCollapsedLines) {
            return
        }

        // Saves the text height w/ max lines
        mTextHeightWithMaxLines = getRealTextViewHeight(mTv!!)

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            mTv!!.maxLines = mMaxCollapsedLines
        }
        mToggleView.visibility = View.VISIBLE

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (mCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            mTv!!.post { mMarginBetweenTxtAndBottom = height - mTv!!.height }
            // Saves the collapsed height of this ViewGroup
            mCollapsedHeight = measuredHeight
        }
    }

    fun setOnExpandStateChangeListener(listener: OnExpandStateChangeListener?) {
        mListener = listener
    }

    fun setText(text: CharSequence?, collapsedStatus: SparseBooleanArray,
                position: Int) {
        mCollapsedStatus = collapsedStatus
        mPosition = position
        val isCollapsed = collapsedStatus.get(position, true)
        clearAnimation()
        mCollapsed = isCollapsed
        mExpandIndicatorController!!.changeState(mCollapsed)
    }

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES)
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION)
        mAnimAlphaStart = typedArray.getFloat(R.styleable.ExpandableTextView_animAlphaStart,
                DEFAULT_ANIM_ALPHA_START)
        mExpandableTextId = typedArray.getResourceId(R.styleable.ExpandableTextView_expandableTextId,
                R.id.expandable_text)
        mExpandCollapseToggleId = typedArray
                .getResourceId(R.styleable.ExpandableTextView_expandCollapseToggleId, R.id.expand_collapse)
        mExpandToggleOnTextClick = typedArray.getBoolean(R.styleable.ExpandableTextView_expandToggleOnTextClick, true)

        mExpandIndicatorController = setupExpandToggleController(context, typedArray)

        typedArray.recycle()

        // enforces vertical orientation
        orientation = LinearLayout.VERTICAL

        // default visibility is gone
        visibility = View.GONE
    }

    private fun findViews() {
        mTv = findViewById<View>(mExpandableTextId) as TextView
        if (mExpandToggleOnTextClick) {
            mTv!!.setOnClickListener(this)
        } else {
            mTv!!.setOnClickListener(null)
        }
        mToggleView = findViewById(mExpandCollapseToggleId)
        mExpandIndicatorController!!.setView(mToggleView)
        mExpandIndicatorController!!.changeState(mCollapsed)
        mToggleView.setOnClickListener(this)
    }

    private fun setupExpandToggleController(context: Context,
                                            typedArray: TypedArray): ExpandIndicatorController {
        val expandToggleType = typedArray.getInt(R.styleable.ExpandableTextView_expandToggleType, DEFAULT_TOGGLE_TYPE)
        val expandIndicatorController: ExpandIndicatorController
        when (expandToggleType) {
            EXPAND_INDICATOR_IMAGE_BUTTON -> {
                var expandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandIndicator)
                var collapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseIndicator)

                if (expandDrawable == null) {
                    expandDrawable = getDrawable(context, R.mipmap.expend_btn)
                }
                if (collapseDrawable == null) {
                    collapseDrawable = getDrawable(context, R.mipmap.reduce_btn)
                }
                expandIndicatorController = ImageButtonExpandController(expandDrawable, collapseDrawable)
            }
            EXPAND_INDICATOR_TEXT_VIEW -> {
                val expandText = typedArray.getString(R.styleable.ExpandableTextView_expandIndicator)
                val collapseText = typedArray.getString(R.styleable.ExpandableTextView_collapseIndicator)
                expandIndicatorController = TextViewExpandController(expandText!!, collapseText!!)
            }
            else -> throw IllegalStateException(
                    "Must be of enum: ExpandableTextView_expandToggleType, one of EXPAND_INDICATOR_IMAGE_BUTTON or EXPAND_INDICATOR_TEXT_VIEW.")
        }

        return expandIndicatorController
    }

    internal inner class ExpandCollapseAnimation(private val mTargetView: View, private val mStartHeight: Int, private val mEndHeight: Int) : Animation() {

        init {
            duration = mAnimationDuration.toLong()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val newHeight = ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight).toInt()
            mTv!!.maxHeight = newHeight - mMarginBetweenTxtAndBottom
            if (java.lang.Float.compare(mAnimAlphaStart, 1.0f) != 0) {
                applyAlphaAnimation(mTv, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart))
            }
            mTargetView.layoutParams.height = newHeight
            mTargetView.requestLayout()
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         *
         * @param textView - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        fun onExpandStateChanged(textView: TextView?, isExpanded: Boolean)
    }

    internal interface ExpandIndicatorController {
        fun changeState(collapsed: Boolean)

        fun setView(toggleView: View)

        fun showView(isshow: Boolean)
    }

    internal inner class ImageButtonExpandController(private val mExpandDrawable: Drawable, private val mCollapseDrawable: Drawable) : ExpandIndicatorController {

        private var mImageButton: ImageButton? = null

        override fun changeState(collapsed: Boolean) {
            mImageButton!!.setImageDrawable(if (collapsed) mExpandDrawable else mCollapseDrawable)
        }

        override fun setView(toggleView: View) {
            mImageButton = toggleView as ImageButton
        }

        override fun showView(isshow: Boolean) {
            if (mImageButton != null) {
                Handler().post {
                    mImageButton!!.clearAnimation()
                    mImageButton!!.visibility = if (isshow) View.VISIBLE else View.GONE
                }
            }
        }
    }

    internal class TextViewExpandController(private val mExpandText: String, private val mCollapseText: String) : ExpandIndicatorController {

        private var mTextView: TextView? = null

        override fun changeState(collapsed: Boolean) {
            mTextView!!.text = if (collapsed) mExpandText else mCollapseText
        }

        override fun setView(toggleView: View) {
            mTextView = toggleView as TextView
        }

        override fun showView(isshow: Boolean) {

        }
    }

    fun showToogleView(isshow: Boolean) {
        if (mExpandIndicatorController != null) {
            mExpandIndicatorController!!.showView(isshow)
        }
    }

    fun expandView() {
        mCollapsed = false
        requestLayout()
    }

    companion object {

        private val TAG = ExpandableTextView::class.java!!.getSimpleName()

        private val EXPAND_INDICATOR_IMAGE_BUTTON = 0

        private val EXPAND_INDICATOR_TEXT_VIEW = 1

        private val DEFAULT_TOGGLE_TYPE = EXPAND_INDICATOR_IMAGE_BUTTON

        /* The default number of lines */
        private val MAX_COLLAPSED_LINES = 8

        /* The default animation duration */
        private val DEFAULT_ANIM_DURATION = 300

        /* The default alpha value when the animation starts */
        private val DEFAULT_ANIM_ALPHA_START = 0.7f

        private val isPostHoneycomb: Boolean
            get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB

        private val isPostLolipop: Boolean
            get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private fun applyAlphaAnimation(view: View?, alpha: Float) {
            if (isPostHoneycomb) {
                view!!.alpha = alpha
            } else {
                val alphaAnimation = AlphaAnimation(alpha, alpha)
                // make it instant
                alphaAnimation.duration = 0
                alphaAnimation.fillAfter = true
                view!!.startAnimation(alphaAnimation)
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable {
            val resources = context.resources
            return if (isPostLolipop) {
                resources.getDrawable(resId, context.theme)
            } else {
                resources.getDrawable(resId)
            }
        }

        private fun getRealTextViewHeight(textView: TextView): Int {
            val textHeight = textView.layout.getLineTop(textView.lineCount)
            val padding = textView.compoundPaddingTop + textView.compoundPaddingBottom
            return textHeight + padding
        }
    }
}
