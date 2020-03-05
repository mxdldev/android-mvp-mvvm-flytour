package com.fly.tour

import android.content.Context
import android.util.Log
import com.didi.virtualapk.PluginManager
import com.fly.tour.common.BaseApplication
import com.fly.tour.common.base.BaseActivity
import java.io.File
import java.io.FileOutputStream

/**
 * Description: <MyApplication><br></br>
 * Author:      mxdl<br></br>
 * Date:        2018/12/27<br></br>
 * Version:     V1.0.0<br></br>
 * Update:     <br></br>
</MyApplication> */
class MyApplication : BaseApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        PluginManager.getInstance(base).init()
    }

    override fun onCreate() {
        super.onCreate()
        loadPlugin(this, "plugin_fly_news-release.apk")
        loadPlugin(this, "plugin_fly_find-release.apk")
        loadPlugin(this, "plugin_fly_me-release.apk")
    }
    private fun loadPlugin(base: Context, apkname: String) {
        try {
            val pluginManager = PluginManager.getInstance(base)
            val file = File(base.filesDir, apkname)
            val inputStream = base.assets.open(apkname, 2)
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
            pluginManager.loadPlugin(file)
            Log.i(BaseActivity.TAG, "Loaded plugin from assets: $file")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}