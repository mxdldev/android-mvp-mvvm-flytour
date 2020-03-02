package com.fly.tour.common.util

import android.text.TextUtils

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Description: <FileUtil><br>
 * Author:      mxdl<br>
 * Date:        2018/7/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</FileUtil> */
object FileUtil {
    fun isImageFile(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg = ".+(\\.jpeg|\\.jpg|\\.gif|\\.bmp|\\.png).*"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(url.toLowerCase())
        return matcher.find()
    }

    fun isVideoFile(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg = ".+(\\.avi|\\.wmv|\\.mpeg|\\.mp4|\\.mov|\\.mkv|\\.flv|\\.f4v|\\.m4v|\\.rmvb|\\.rm|\\.rmvb|\\.3gp|\\.dat|\\.ts|\\.mts|\\.vob).*"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(url.toLowerCase())
        return matcher.find()
    }

    fun isUrl(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"
        return url.matches(reg.toRegex())
    }
}
