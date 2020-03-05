package com.fly.tour.common.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.content.res.AssetManager
import android.net.Uri
import android.os.Environment
import android.text.TextUtils

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale

/**
 * Description: <单位转换工具类><br>
 *
 * <h1>基本功能：</h1>
 *  * 1.获取包名
 *  * 2.获取当前应用的版本号
 *  * 3.获取当前应用的版本名称
 *  * 4.获取应用的名称
 *  * 5.获取assets下资源
 *  * 6.获取系统语言
 *  * 7.获取assets下资源
 *  * 8.app的是否在运行
 *  * 9.app是否在前台运行
 *  * 10.当前Activity是否在栈顶>
 *  * 11.启动应用
 *  * 12.获取assets下资源
 * <h1>存贮相关</h1>
 *  * 1.外部存储是否可读写
 *  * 2.获取外部存储文件夹
 *  * 3.获取外部存储路径
 * <h1>APK相关操作</h1>
 *  * 1.安装APK
 *  * 2.卸载APK
 *  * 3.获得APK包名
 *  * 4.判断是否已经安装
 *
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</单位转换工具类> */

class EnvironmentUtil {

    /**
     * 当前app的是否在运行
     *
     * @return false: 说明app不在当前系统的栈中; true:当前app正处于用户使用状态(包含在Home在后台)
     */

    @SuppressLint("NewApi")
    fun isAppRunning(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfos = activityManager.getRunningTasks(100) ?: return false
        for (taskInfo in runningTaskInfos)
            if (context.packageName == taskInfo.baseActivity!!.packageName) {
                return true
            }
        return false
    }

    /**
     * 判断当前Activity是否排在栈顶
     *
     * @return
     */
    @SuppressLint("NewApi")
    protected fun isTopActivity(context: Context, clazz: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfos = manager.getRunningTasks(1)
        return if (runningTaskInfos != null && !runningTaskInfos.isEmpty()
                && clazz.name == runningTaskInfos[0].topActivity!!.className) {
            true
        } else {
            false
        }
    }

    /**
     * 存储信息
     */
    object Storage {

        /**
         * 外部存储是否可读写
         *
         * @return 可读写返回true, 否则返回false
         */
        val isExternalStorageWritable: Boolean
            get() {
                val state = Environment.getExternalStorageState()
                return Environment.MEDIA_MOUNTED == state
            }

        /**
         * 获取外部目录缓存路径
         *
         * @param context
         * context
         * @return 外部存储换成路径
         */
        fun getExternalCacheDir(context: Context): File? {
            var file: File? = null
            if (SdkVersionUtil.hasFroyo()) {
                file = context.externalCacheDir
            }

            if (file == null) {
                val cacheDir = "/Android/data/" + context.packageName + "/cache/"
                file = File(Environment.getExternalStorageDirectory().path + cacheDir)

                file.mkdirs()

                if (file.isDirectory) {
                    return file
                }
            }
            return null
        }

        /**
         * 获取缓存路径
         *
         * @param context
         * context
         * @return 存储路径
         */
        fun getCachePath(context: Context): String {
            var file: File? = null
            if (isExternalStorageWritable) {
                file = getExternalCacheDir(context)
            }

            return if (file != null) file.absolutePath else context.cacheDir.absolutePath
        }

    }

    /**
     * APK相关操作
     */
    object Package {

        /**
         * 安装APK
         *
         * @param context
         * @param file
         */
        fun installApp(context: Context, file: File) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            context.startActivity(intent)
        }

        /**
         * 卸载APK
         *
         * @param context
         * @param packageName
         */
        fun unInstallApp(context: Context, packageName: String) {
            val packageUri = Uri.parse("package:$packageName")
            val intent = Intent(Intent.ACTION_DELETE, packageUri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        /**
         * 获得APK包名
         *
         * @param context
         * @param apkFile
         * @return
         */
        fun getApkFilePackage(context: Context, apkFile: File): String? {
            val pm = context.packageManager
            val info = pm.getPackageArchiveInfo(apkFile.path, PackageManager.GET_ACTIVITIES)
            return info?.applicationInfo?.packageName
        }

        /**
         * 判断是否已经安装
         *
         * @param context
         * @param packageName
         * @return
         */
        fun isAppInstalled(context: Context, packageName: String): Boolean {
            if (TextUtils.isEmpty(packageName))
                return false
            try {
                context.packageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES)
                return true
            } catch (e: NameNotFoundException) {
                return false
            }

        }
    }

    companion object {
        private val TAG = "EnvironmentUtil"

        /**
         * 获取包名
         *
         * @return 包名
         */
        fun getPackageName(context: Context): String {
            return context.packageName
        }

        /**
         * 获取当前应用的版本号
         */
        fun getAppVersionCode(context: Context): Int {
            // 获取手机的包管理者
            val pm = context.packageManager
            try {
                val packInfo = pm.getPackageInfo(getPackageName(context), 0)
                return packInfo.versionCode
            } catch (e: NameNotFoundException) {
                e.printStackTrace()
                return 0
            }

        }

        /**
         * 获取当前应用的版本名称
         */
        fun getAppVersionName(context: Context): String {
            // 获取手机的包管理者
            val pm = context.packageManager
            try {
                val packInfo = pm.getPackageInfo(getPackageName(context), 0)
                return packInfo.versionName
            } catch (e: NameNotFoundException) {
                e.printStackTrace()
                // 不可能发生.
                return ""
            }

        }

        /**
         * 获取应用的名称
         *
         * @return
         */
        fun getApplicationName(context: Context): String {
            var packageManager: PackageManager? = null
            var applicationInfo: ApplicationInfo? = null
            try {
                packageManager = context.packageManager
                applicationInfo = packageManager!!.getApplicationInfo(getPackageName(context), 0)
            } catch (e: NameNotFoundException) {
                applicationInfo = null
            }

            val applicationName = packageManager!!.getApplicationLabel(applicationInfo) as String
            println(applicationName)
            return applicationName
        }

        /**
         * 获取assets下资源
         *
         * @param context
         * @param fileName
         * @return
         */
        fun getAssetsString(context: Context, fileName: String): String {
            val stringBuilder = StringBuilder()
            try {
                val assetManager = context.assets
                val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
                var line: String? = null
                while (({line = bf.readLine()}) != null) {
                    stringBuilder.append(line)
                }
                bf.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return stringBuilder.toString()
        }

        /**
         * 获取系统语言
         *
         * @param context
         * @return
         * @param context
         * @return
         */
        fun getSystemLanguage(context: Context): String {
            val locale = context.resources.configuration.locale
            return locale.language
        }

        /**
         * app是否在前台
         *
         * @param context
         * @return
         */
        fun isForeground(context: Context): Boolean {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses
            for (appProcess in appProcesses) {
                if (appProcess.processName == context.packageName) {
                    return if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        true
                    } else {
                        false
                    }
                }
            }
            return false
        }

        /**
         * 返回app运行状态 1:程序在前台运行 2:程序在后台运行 3:程序未启动 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS"></uses-permission>
         */
        @SuppressLint("NewApi")
        fun getAppStatus(context: Context): Int {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(20)
            // 判断程序是否在栈顶
            if (list[0].topActivity!!.packageName == context.packageName) {
                return 1
            } else {
                // 判断程序是否在栈里
                for (info in list) {
                    if (info.topActivity!!.packageName == context.packageName) {
                        return 2
                    }
                }
                return 3// 栈里找不到，返回3
            }
        }

        /**
         * 启动应用
         *
         * @param context
         * @param className
         */
        fun openApp(context: Context, className: String) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(context.packageName, className)
            intent.component = cn
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}