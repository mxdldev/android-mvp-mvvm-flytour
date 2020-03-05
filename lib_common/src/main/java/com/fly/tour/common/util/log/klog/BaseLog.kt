package com.fly.tour.common.util.log.klog

import android.util.Log

import com.fly.tour.common.util.log.KLog

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
object BaseLog {

    private val MAX_LENGTH = 4000

    fun printDefault(type: Int, tag: String, msg: String) {

        var index = 0
        val length = msg.length
        val countOfSub = length / MAX_LENGTH

        if (countOfSub > 0) {
            for (i in 0 until countOfSub) {
                val sub = msg.substring(index, index + MAX_LENGTH)
                printSub(type, tag, sub)
                index += MAX_LENGTH
            }
            printSub(type, tag, msg.substring(index, length))
        } else {
            printSub(type, tag, msg)
        }
    }

    private fun printSub(type: Int, tag: String, sub: String) {
        when (type) {
            KLog.V -> Log.v(tag, sub)
            KLog.D -> Log.d(tag, sub)
            KLog.I -> Log.i(tag, sub)
            KLog.W -> Log.w(tag, sub)
            KLog.E -> Log.e(tag, sub)
            KLog.A -> Log.wtf(tag, sub)
        }
    }

}
