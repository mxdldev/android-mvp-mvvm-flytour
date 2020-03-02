package com.fly.tour.common.util.log.klog

import android.util.Log

import com.fly.tour.common.util.log.KLog
import com.fly.tour.common.util.log.KLogUtil

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
object JsonLog {

    fun printJson(tag: String, msg: String, headString: String) {

        var message: String

        try {
            if (msg.startsWith("{")) {
                val jsonObject = JSONObject(msg)
                message = jsonObject.toString(KLog.JSON_INDENT)
            } else if (msg.startsWith("[")) {
                val jsonArray = JSONArray(msg)
                message = jsonArray.toString(KLog.JSON_INDENT)
            } else {
                message = msg
            }
        } catch (e: JSONException) {
            message = msg
        }

        KLogUtil.printLine(tag, true)
        message = headString + KLog.LINE_SEPARATOR + message
        val lines = message.split(KLog.LINE_SEPARATOR!!.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        for (line in lines) {
            Log.d(tag, "║ $line")
        }
        KLogUtil.printLine(tag, false)
    }
}
