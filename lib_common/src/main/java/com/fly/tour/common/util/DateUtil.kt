package com.fly.tour.common.util

import android.text.TextUtils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Description: <DateUtil><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</DateUtil> */
object DateUtil {
    /**
     * 获取当前时间的位置：一天24小时以半小时为单位划分为48个单元格
     * @return
     */
    val currTimePosition: Int
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            return Math.ceil((calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)).toDouble() / 30).toInt()
        }

    enum class FormatType {
        yyyy, yyyyMM, yyyyMMdd, yyyyMMddHHmm, yyyyMMddHHmmss, MMdd, HHmm, MM, dd, MMddHHmm
    }

    /**
     * 格式化时间字符串
     */
    fun formatDate(time: String, formatStr: String): String {
        val setdate = parseTime(time)
        val sdf = SimpleDateFormat(formatStr)
        return sdf.format(setdate)
    }

    fun formatDate(time: String, type: FormatType): String {
        if (TextUtils.isEmpty(time)) {
            return ""
        }
        val date = parseTime(time)
        return formatDate(date, type)
    }

    fun formatDate(time: String, fromtype: FormatType, totype: FormatType): String {
        if (TextUtils.isEmpty(time)) {
            return ""
        }
        val date = parseTime(time, fromtype)
        return formatDate(date, totype)
    }

    fun formatDate(time: Date?, type: FormatType): String {
        if (time == null) {
            return ""
        }
        val sdf = getSimpleDateFormat(type)
        return sdf.format(time)
    }

    private fun getSimpleDateFormat(type: FormatType): SimpleDateFormat {
        val sdf: SimpleDateFormat
        when (type) {
            DateUtil.FormatType.yyyy -> sdf = SimpleDateFormat("yyyy")
            DateUtil.FormatType.yyyyMM -> sdf = SimpleDateFormat("yyyy-MM")
            DateUtil.FormatType.yyyyMMdd -> sdf = SimpleDateFormat("yyyy-MM-dd")
            DateUtil.FormatType.yyyyMMddHHmm -> sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            DateUtil.FormatType.yyyyMMddHHmmss -> sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            DateUtil.FormatType.MMdd -> sdf = SimpleDateFormat("MM-dd")
            DateUtil.FormatType.HHmm -> sdf = SimpleDateFormat("HH:mm")
            DateUtil.FormatType.MM -> sdf = SimpleDateFormat("MM")
            DateUtil.FormatType.dd -> sdf = SimpleDateFormat("dd")
            DateUtil.FormatType.MMddHHmm -> sdf = SimpleDateFormat("MM-dd HH:mm")
            else -> sdf = SimpleDateFormat("yyyy-MM-dd")
        }
        return sdf
    }

    /**
     * 将时间字符串转化为date
     */
    fun parseTime(dateStr: String, formatStr: String): Date? {
        var date: Date? = null
        try {
            val sdf = SimpleDateFormat(formatStr)
            date = sdf.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 将字符串转换成date
     */
    fun parseTime(time: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date? = null
        try {
            date = sdf.parse(time)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 将字符串转换成date
     */
    fun parseTime(time: String, type: FormatType): Date? {
        var date: Date? = null
        val sdf = getSimpleDateFormat(type)
        try {
            date = sdf.parse(time)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 增/减 offset 后的日期
     */
    fun addAndSubtractDate(offset: Int, date: Date, unit: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (unit == Calendar.MONTH) {
            calendar.set(Calendar.DATE, 1)
        }
        calendar.set(unit, calendar.get(unit) + offset)
        return calendar.time
    }

    /**
     * 计算两个日期之间相差的天数
     */
    @Throws(ParseException::class)
    fun daysBetween(smdate: Date, bdate: Date): Int {
        var smdate = smdate
        var bdate = bdate
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        smdate = sdf.parse(sdf.format(smdate))
        bdate = sdf.parse(sdf.format(bdate))
        val cal = Calendar.getInstance()
        cal.time = smdate
        val time1 = cal.timeInMillis
        cal.time = bdate
        val time2 = cal.timeInMillis
        val between_days = (time2 - time1) / (1000 * 3600 * 24)

        return Integer.parseInt(between_days.toString())
    }

    /**
     * 比较日期大小
     */
    @Throws(ParseException::class)
    fun compareDate(smdate: Date, bdate: Date): Int {
        var smdate = smdate
        var bdate = bdate
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        smdate = sdf.parse(sdf.format(smdate))
        bdate = sdf.parse(sdf.format(bdate))
        val cal = Calendar.getInstance()
        cal.time = smdate
        val time1 = cal.timeInMillis
        cal.time = bdate
        val time2 = cal.timeInMillis
        return if (time1 == time2) {
            0
        } else if (time1 > time2) {
            -1
        } else {
            1
        }

    }

    /**
     * 根据日期算周几
     */
    fun whatDay(date: Date): String {
        val calendar = Calendar.getInstance()// 获得一个日历
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        calendar.set(year, month, day)// 设置当前时间,月份是从0月开始计算
        val number = calendar.get(Calendar.DAY_OF_WEEK)// 周表示1-7，是从周日开始，
        val str = arrayOf("", "周日", "周一", "周二", "周三", "周四", "周五", "周六")
        return str[number]
    }

    fun formatSecondToHourMinuteSecond(second: Int): String {
        var h = 0
        var d = 0
        var s = 0
        val temp = second % 3600
        if (second > 3600) {
            h = second / 3600
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60
                    if (temp % 60 != 0) {
                        s = temp % 60
                    }
                } else {
                    s = temp
                }
            }
        } else {
            d = second / 60
            if (second % 60 != 0) {
                s = second % 60
            }
        }
        return h.toString() + "时" + d + "分" + s + "秒"
    }

    fun formatSecondToHourMinute(duration: Int): String {
        if (duration < 60) {
            return duration.toString() + "秒"
        } else if (duration < 60 * 60) {
            return Math.round(duration.toFloat() / 60).toString() + "分钟"
        } else {
            val second = duration % (60 * 60)
            if (second == 0) {
                return (duration / (60 * 60)).toString() + "小时"
            } else {

                val round = Math.round(second.toFloat() / 60)
                return if (round == 0) {
                    (duration / (60 * 60)).toString() + "小时"
                } else {
                    if (round == 60) {
                        (duration / (60 * 60) + 1).toString() + "小时"
                    } else {
                        (duration / (60 * 60)).toString() + "小时" + round + "分钟"
                    }
                }
            }
        }
    }

    /**
     * 说明 小于1分钟：”刚刚“ 小于1小时：”X分钟前“ 小于一天：”X小时前“ 小于一月：”X天前“ 小于一年：6-23 大于一年：2015-6-23
     *
     * @param dateStr
     * @return
     */
    fun formatTimeToDay(dateStr: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdf2 = SimpleDateFormat("MM-dd")
        val sdf3 = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = sdf.parse(dateStr)

            val minute = ((System.currentTimeMillis() - date.time) / 1000 / 60).toInt()
            return if (minute <= 0) {
                "刚刚"

            } else if (minute / 60 == 0) {
                minute.toString() + "分钟前"

            } else if (minute / (60 * 24) == 0) {
                (minute / 60).toString() + "小时前"

            } else if (minute / (60 * 24 * 30) == 0) {
                (minute / (60 * 24)).toString() + "天前"
            } else if (minute / (60 * 24 * 30 * 12) == 0) {
                sdf2.format(date)
            } else {
                sdf3.format(date)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dateStr
    }

    /**
     * 获取几个小时以后的时间戳
     *
     * @param hour
     * @return
     */
    fun getLaterTimeByHour(hour: Int): String {
        val d = Date()
        val now = Calendar.getInstance()
        now.time = d
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour)
        // now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 30);
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(now.time)
    }

    /**
     * 获取几天以后的时间戳
     *
     * @param day
     * @return
     */
    fun getLaterTimeByDay(day: Int): String {
        return getLaterTimeByHour(day * 24)
    }

    /**
     * 获取给定时间以后几天的时间戳
     * @param date
     * @param day
     * @return
     */
    fun getLaterTimeByDay(date: String, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.time = parseTime(date, FormatType.yyyyMMdd)
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + day * 24)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(calendar.time)
    }
}