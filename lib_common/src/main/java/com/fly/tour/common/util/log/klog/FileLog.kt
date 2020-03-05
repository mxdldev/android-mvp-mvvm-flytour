package com.fly.tour.common.util.log.klog

import android.util.Log

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.util.Random

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
object FileLog {

    private val FILE_PREFIX = "KLog_"
    private val FILE_FORMAT = ".log"

    private val fileName: String
        get() {
            val random = Random()
            return FILE_PREFIX + java.lang.Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT
        }

    fun printFile(tag: String, targetDirectory: File, fileName: String?, headString: String, msg: String) {
        var fileName = fileName

        fileName = fileName ?: fileName
        if (save(targetDirectory, fileName!!, msg)) {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.absolutePath + "/" + fileName)
        } else {
            Log.e(tag, headString + "save log fails !")
        }
    }

    private fun save(dic: File, fileName: String, msg: String): Boolean {

        val file = File(dic, fileName)

        try {
            val outputStream = FileOutputStream(file)
            val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
            outputStreamWriter.write(msg)
            outputStreamWriter.flush()
            outputStream.close()
            return true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

}
