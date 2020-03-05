package com.fly.tour.common.util.log


import android.text.TextUtils

import com.fly.tour.common.util.log.klog.BaseLog
import com.fly.tour.common.util.log.klog.FileLog
import com.fly.tour.common.util.log.klog.JsonLog
import com.fly.tour.common.util.log.klog.XmlLog

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * This is a Log tool，with this you can the following
 *
 *  1. use KLog.d(),you could print whether the method execute,and the default tag is current class's name
 *  1. use KLog.d(msg),you could print log as before,and you could location the method with a click in Android Studio Logcat
 *  1. use KLog.json(),you could print json string with well format automatic
 *
 *
 * @author zhaokaiqiang
 * github https://github.com/ZhaoKaiQiang/KLog
 * 15/11/17 扩展功能，添加对文件的支持
 * 15/11/18 扩展功能，增加对XML的支持，修复BUG
 * 15/12/8  扩展功能，添加对任意参数的支持
 * 15/12/11 扩展功能，增加对无限长字符串支持
 * 16/6/13  扩展功能，添加对自定义全局Tag的支持,修复内部类不能点击跳转的BUG
 * 16/6/15  扩展功能，添加不能关闭的KLog.debug(),用于发布版本的Log打印,优化部分代码
 * 16/6/20  扩展功能，添加堆栈跟踪功能KLog.trace()
 */
object KLog {

    val LINE_SEPARATOR = System.getProperty("line.separator")
    val NULL_TIPS = "Log with null object"

    private val DEFAULT_MESSAGE = "execute"
    private val PARAM = "Param"
    private val NULL = "null"
    private val TAG_DEFAULT = "KLog"
    private val SUFFIX = ".java"

    val JSON_INDENT = 4

    val V = 0x1
    val D = 0x2
    val I = 0x3
    val W = 0x4
    val E = 0x5
    val A = 0x6

    private val JSON = 0x7
    private val XML = 0x8

    private val STACK_TRACE_INDEX_5 = 5
    private val STACK_TRACE_INDEX_4 = 4

    private var mGlobalTag: String? = null
    private var mIsGlobalTagEmpty = true
    private var IS_SHOW_LOG = true

    fun init(isShowLog: Boolean) {
        IS_SHOW_LOG = isShowLog
    }

    fun init(isShowLog: Boolean, tag: String?) {
        IS_SHOW_LOG = isShowLog
        mGlobalTag = tag
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag)
    }

    fun v() {
        printLog(V, null, DEFAULT_MESSAGE)
    }

    fun v(msg: Any) {
        printLog(V, null, msg)
    }

    fun v(tag: String, vararg objects: Any) {
        printLog(V, tag, *objects)
    }

    fun d() {
        printLog(D, null, DEFAULT_MESSAGE)
    }

    fun d(msg: Any) {
        printLog(D, null, msg)
    }

    fun d(tag: String, vararg objects: Any) {
        printLog(D, tag, *objects)
    }

    fun i() {
        printLog(I, null, DEFAULT_MESSAGE)
    }

    fun i(msg: Any) {
        printLog(I, null, msg)
    }

    fun i(tag: String, vararg objects: Any) {
        printLog(I, tag, *objects)
    }

    fun w() {
        printLog(W, null, DEFAULT_MESSAGE)
    }

    fun w(msg: Any) {
        printLog(W, null, msg)
    }

    fun w(tag: String, vararg objects: Any) {
        printLog(W, tag, *objects)
    }

    fun e() {
        printLog(E, null, DEFAULT_MESSAGE)
    }

    fun e(msg: Any) {
        printLog(E, null, msg)
    }

    fun e(tag: String, vararg objects: Any) {
        printLog(E, tag, *objects)
    }

    fun a() {
        printLog(A, null, DEFAULT_MESSAGE)
    }

    fun a(msg: Any) {
        printLog(A, null, msg)
    }

    fun a(tag: String, vararg objects: Any) {
        printLog(A, tag, *objects)
    }

    fun json(jsonFormat: String) {
        printLog(JSON, null, jsonFormat)
    }

    fun json(tag: String, jsonFormat: String) {
        printLog(JSON, tag, jsonFormat)
    }

    fun xml(xml: String) {
        printLog(XML, null, xml)
    }

    fun xml(tag: String, xml: String) {
        printLog(XML, tag, xml)
    }

    fun file(targetDirectory: File, msg: Any) {
        printFile(null, targetDirectory, null, msg)
    }

    fun file(tag: String, targetDirectory: File, msg: Any) {
        printFile(tag, targetDirectory, null, msg)
    }

    fun file(tag: String, targetDirectory: File, fileName: String, msg: Any) {
        printFile(tag, targetDirectory, fileName, msg)
    }

    fun debug() {
        printDebug(null, DEFAULT_MESSAGE)
    }

    fun debug(msg: Any) {
        printDebug(null, msg)
    }

    fun debug(tag: String, vararg objects: Any) {
        printDebug(tag, *objects)
    }

    fun trace() {
        printStackTrace()
    }

    private fun printStackTrace() {

        if (!IS_SHOW_LOG) {
            return
        }

        val tr = Throwable()
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        val message = sw.toString()

        val traceString = message.split("\\n\\t".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val sb = StringBuilder()
        sb.append("\n")
        for (trace in traceString) {
            if (trace.contains("at com.socks.library.KLog")) {
                continue
            }
            sb.append(trace).append("\n")
        }
        val contents = wrapperContent(STACK_TRACE_INDEX_4, null, sb.toString())
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]
        BaseLog.printDefault(D, tag, headString + msg)
    }

    private fun printLog(type: Int, tagStr: String?, vararg objects: Any) {

        if (!IS_SHOW_LOG) {
            return
        }

        val contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, *objects)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]

        when (type) {
            V, D, I, W, E, A -> BaseLog.printDefault(type, tag, headString + msg)
            JSON -> JsonLog.printJson(tag, msg, headString)
            XML -> XmlLog.printXml(tag, msg, headString)
        }

    }

    private fun printDebug(tagStr: String?, vararg objects: Any) {
        val contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, *objects)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]
        BaseLog.printDefault(D, tag, headString + msg)
    }


    private fun printFile(tagStr: String?, targetDirectory: File, fileName: String?, objectMsg: Any) {

        if (!IS_SHOW_LOG) {
            return
        }

        val contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg)
    }

    private fun wrapperContent(stackTraceIndex: Int, tagStr: String?, vararg objects: Any): Array<String> {

        val stackTrace = Thread.currentThread().stackTrace

        val targetElement = stackTrace[stackTraceIndex]
        var className = targetElement.className
        val classNameInfo = className.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        if (classNameInfo.size > 0) {
            className = classNameInfo[classNameInfo.size - 1] + SUFFIX
        }

        if (className.contains("$")) {
            className = className.split("\\$".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0] + SUFFIX
        }

        val methodName = targetElement.methodName
        var lineNumber = targetElement.lineNumber

        if (lineNumber < 0) {
            lineNumber = 0
        }

        var tag: String? = tagStr ?: className

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag
        }

        val msg = if (objects == null) NULL_TIPS else getObjectsString(*objects)
        val headString = "[ ($className:$lineNumber)#$methodName ] "

        return arrayOf<String>(tag!!, msg, headString)
    }

    private fun getObjectsString(vararg objects: Any): String {

        if (objects.size > 1) {
            val stringBuilder = StringBuilder()
            stringBuilder.append("\n")
            for (i in objects.indices) {
                val `object` = objects[i]
                if (`object` == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n")
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(`object`.toString()).append("\n")
                }
            }
            return stringBuilder.toString()
        } else {
            val `object` = objects[0]
            return `object`?.toString() ?: NULL
        }
    }

}
