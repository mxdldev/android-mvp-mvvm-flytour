package com.fly.tour.common.manager

import android.app.Activity
import android.content.Context
import com.fly.tour.common.manager.ActivityManager.Companion.activityStack

import java.util.Stack

/**
 * Description: <ActivityManager><br>
 * Author:      mxdl<br>
 * Date:        2018/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</ActivityManager> */
class ActivityManager private constructor() {

    val isActivityStackEmpty: Boolean
        get() = activityStack.empty()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        try {
            return activityStack.lastElement()
        } catch (e: Exception) {
            return null
        }

    }

    /**
     * 获取当前Activity的前一个Activity
     */
    fun preActivity(): Activity? {
        val index = activityStack.size - 2
        return if (index < 0) {
            null
        } else activityStack[index]
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        activityStack.remove(activity)
        activity?.finish()
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        activityStack.remove(activity)
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        try {
            for (activity in activityStack) {
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack.size
        while (i < size) {
            if (null != activityStack[i]) {
                activityStack[i].finish()
            }
            i++
        }
        activityStack.clear()
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    fun returnToActivity(cls: Class<*>) {
        while (activityStack.size != 0)
            if (activityStack.peek().javaClass == cls) {
                break
            } else {
                finishActivity(activityStack.peek())
            }
    }


    /**
     * 是否已经打开指定的activity
     * @param cls
     * @return
     */
    fun isOpenActivity(cls: Class<*>): Boolean {
        if (activityStack != null) {
            var i = 0
            val size = activityStack.size
            while (i < size) {
                if (cls == activityStack.peek().javaClass) {
                    return true
                }
                i++
            }
        }
        return false
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    fun appExit(context: Context, isBackground: Boolean?) {
        try {
            finishAllActivity()
            val activityMgr =
                context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            activityMgr.restartPackage(context.packageName)
        } catch (e: Exception) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if ((!isBackground!!)!!) {
                System.exit(0)
            }
        }
    }

    companion object {
        private lateinit var activityStack: Stack<Activity>
        @Volatile
        private var instance: ActivityManager? = null

        fun getInstance(): ActivityManager? {
            if (instance == null) {
                synchronized(ActivityManager::class.java) {
                    if (instance == null) {
                        instance = ActivityManager()
                        activityStack = Stack()
                    }
                }

            }
            return instance
        }
    }
}
