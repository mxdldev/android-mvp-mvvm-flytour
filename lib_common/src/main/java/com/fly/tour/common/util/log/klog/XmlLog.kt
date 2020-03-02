package com.fly.tour.common.util.log.klog

import android.util.Log

import com.fly.tour.common.util.log.KLog
import com.fly.tour.common.util.log.KLogUtil

import java.io.StringReader
import java.io.StringWriter

import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
object XmlLog {

    fun printXml(tag: String, xml: String?, headString: String) {
        var xml = xml

        if (xml != null) {
            xml = XmlLog.formatXML(xml)
            xml = headString + "\n" + xml
        } else {
            xml = headString + KLog.NULL_TIPS
        }

        KLogUtil.printLine(tag, true)
        val lines = xml.split(KLog.LINE_SEPARATOR!!.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        for (line in lines) {
            if (!KLogUtil.isEmpty(line)) {
                Log.d(tag, "║ $line")
            }
        }
        KLogUtil.printLine(tag, false)
    }

    private fun formatXML(inputXML: String): String {
        try {
            val xmlInput = StreamSource(StringReader(inputXML))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            return xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
        } catch (e: Exception) {
            e.printStackTrace()
            return inputXML
        }

    }

}
