package com.fly.tour.common.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.FilenameFilter
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import java.util.Properties
import java.util.TreeSet

/**
 * 线程未捕获异常控制器是用来处理未捕获异常的。
 *
 *
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
class CrashHandler
/**
 * 保证只有一个CrashHandler实例
 */
private constructor() : Thread.UncaughtExceptionHandler {
    /**
     * 程序的Context对象
     */
    private var mContext: Context? = null
    /**
     * 系统默认的UncaughtException处理类
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    private val mDeviceCrashInfo = Properties()

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    fun init(ctx: Context) {
        mContext = ctx
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            // MobclickAgent.reportError(context, ex);
            // 退出程序
            ToastUtil.showToast("发生异常，已重启应用")
            // EnvironmentUtil.openApp(mContext, SplashActivity.class.getName());
            // Intent intent = new Intent(Intent.ACTION_MAIN);
            // intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // ComponentName cn = new ComponentName(mContext.getPackageName(), className);
            // intent.setComponent(cn);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // mContext.startActivity(intent);

            val packageName = mContext!!.packageName
            // Log.v("TEST","packageName:"+packageName);

            val intent = mContext!!.packageManager.getLaunchIntentForPackage(packageName)
            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            mContext!!.startActivity(intent)

            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return true
        }
        // final String msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare()

                ToastUtil.showToast("程序异常，请重启应用")
                Looper.loop()
                // 程序出现异常，下次程序进入默认值更改为我的主页
                // SaveSharedPreference.setSharedPreferenceInt(mContext,
                // "lastSelect", R.id.rb_index_page);
            }
        }.start()
        // 收集设备信息
        collectCrashDeviceInfo(mContext)
        // 保存错误报告文件
        // saveCrashInfoToFile(ex);
        // 发送错误报告到服务器
        // sendCrashReportsToServer(mContext);
        return true
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    fun collectCrashDeviceInfo(ctx: Context?) {
        try {

            val pm = ctx!!.packageManager

            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {

                mDeviceCrashInfo[VERSION_NAME] = if (pi.versionName == null) "not set" else pi.versionName

                mDeviceCrashInfo[VERSION_CODE] = pi.versionCode
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error while collect package info$e")
        }

        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        val fields = Build::class.java!!.getDeclaredFields()
        for (field in fields) {
            try {
                // setAccessible(boolean flag)
                // 将此对象的 accessible 标志设置为指示的布尔值。
                // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
                field.setAccessible(true)
                mDeviceCrashInfo[field.getName()] = field.get(null)
                if (DEBUG) {
                    Log.d(TAG, field.getName() + " : " + field.get(null))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error while collect crash info$e")
            }

        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private fun saveCrashInfoToFile(ex: Throwable): String? {

        val info = StringWriter()
        val printWriter = PrintWriter(info)
        // printStackTrace(PrintWriter s)
        // 将此 throwable 及其追踪输出到指定的 PrintWriter
        ex.printStackTrace(printWriter)

        // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }

        // toString() 以字符串的形式返回该缓冲区的当前值。
        val result = info.toString()
        printWriter.close()
        mDeviceCrashInfo[STACK_TRACE] = result

        try {

            val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            val fileName = "crash-$timestamp$CRASH_REPORTER_EXTENSION"
            // 保存文件
            val directory = File(EnvironmentUtil.Storage.getExternalCacheDir(mContext!!), "logs")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            val trace = FileOutputStream(file)

            Log.e(TAG, String(result.toByteArray()))
            // 写入
            trace.write(result.toByteArray())
            trace.flush()
            trace.close()
            return fileName
        } catch (e: Exception) {

            Log.e(TAG, e.toString())
        }

        return null
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx
     */
    private fun sendCrashReportsToServer(ctx: Context?) {
        val crFiles = getCrashReportFiles(ctx!!)
        if (crFiles != null && crFiles.size > 0) {
            val sortedFiles = TreeSet<String>()
            sortedFiles.addAll(Arrays.asList(*crFiles))

            for (fileName in sortedFiles) {
                val cr = File(ctx.filesDir, fileName)
                postReport(cr)
                cr.delete()// 删除已发送的报告
            }
        }
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private fun getCrashReportFiles(ctx: Context): Array<String>? {
        val filesDir = ctx.filesDir
        // 实现FilenameFilter接口的类实例可用于过滤器文件名
        val filter = FilenameFilter { dir, name -> // accept(File dir, String name)
            // 测试指定文件是否应该包含在某一文件列表中。
            name.endsWith(CRASH_REPORTER_EXTENSION)
        }
        // list(FilenameFilter filter)
        // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
        return filesDir.list(filter)
    }

    private fun postReport(file: File) {
        // 使用HTTP Post 发送错误报告到服务器
        // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    fun sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext)

    }

    companion object {
        /**
         * Debug Log Tag
         */
        val TAG = "YESWAY-CrashHandler"
        /**
         * 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能
         */
        val DEBUG = true
        /**
         * CrashHandler实例
         */
        private var mCrashHandler: CrashHandler? = null
        /**
         * 版本
         */
        private val VERSION_NAME = "versionName"
        /**
         * 版本号
         */
        private val VERSION_CODE = "versionCode"
        /**
         * 堆栈名
         */
        private val STACK_TRACE = "STACK_TRACE"
        /**
         * 错误报告文件的扩展名
         */
        private val CRASH_REPORTER_EXTENSION = ".txt"

        /**
         * 获取CrashHandler实例 ,单例模式
         */
        val instance: CrashHandler
            get() {
                if (mCrashHandler == null) {
                    mCrashHandler = CrashHandler()
                }
                return mCrashHandler as CrashHandler
            }

        private fun openApp(context: Context, className: String) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(context.packageName, className)
            intent.component = cn
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}
