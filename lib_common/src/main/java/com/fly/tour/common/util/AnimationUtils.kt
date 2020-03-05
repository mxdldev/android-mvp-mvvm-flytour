package com.fly.tour.common.util

import android.view.animation.Animation
import android.view.animation.TranslateAnimation

/**
 * @Description
 * @Author guojingbu
 * @Date 2019/3/12
 */
object AnimationUtils {
    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    fun moveToBottom(): TranslateAnimation {

        val hiddenAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f)
        hiddenAction.duration = 300
        return hiddenAction
    }

    fun moveToLocation(): TranslateAnimation {
        val visibleAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f)
        visibleAction.duration = 300
        return visibleAction

    }
}
