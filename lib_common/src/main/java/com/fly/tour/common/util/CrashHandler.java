package com.zjx.vcars.common.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;

/**
 * 线程未捕获异常控制器是用来处理未捕获异常的。
 * <p>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
  /**
   * Debug Log Tag
   */
  public static final String TAG = "YESWAY-CrashHandler";
  /**
   * 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能
   */
  public static final boolean DEBUG = true;
  /**
   * CrashHandler实例
   */
  private static CrashHandler mCrashHandler;
  /**
   * 程序的Context对象
   */
  private Context mContext;
  /**
   * 系统默认的UncaughtException处理类
   */
  private Thread.UncaughtExceptionHandler mDefaultHandler;
  /**
   * 使用Properties来保存设备的信息和错误堆栈信息
   */
  private Properties mDeviceCrashInfo = new Properties();
  /**
   * 版本
   */
  private static final String VERSION_NAME = "versionName";
  /**
   * 版本号
   */
  private static final String VERSION_CODE = "versionCode";
  /**
   * 堆栈名
   */
  private static final String STACK_TRACE = "STACK_TRACE";
  /**
   * 错误报告文件的扩展名
   */
  private static final String CRASH_REPORTER_EXTENSION = ".txt";

  /**
   * 保证只有一个CrashHandler实例
   */
  private CrashHandler() {

  }

  /**
   * 获取CrashHandler实例 ,单例模式
   */
  public static CrashHandler getInstance() {
    if (mCrashHandler == null) {
      mCrashHandler = new CrashHandler();
    }
    return mCrashHandler;
  }

  /**
   * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
   *
   * @param ctx
   */
  public void init(Context ctx) {
    mContext = ctx;
    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  /**
   * 当UncaughtException发生时会转入该函数来处理
   */
  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    if (!handleException(ex) && mDefaultHandler != null) {
      // 如果用户没有处理则让系统默认的异常处理器来处理
      mDefaultHandler.uncaughtException(thread, ex);
    } else {
      // MobclickAgent.reportError(context, ex);
      // 退出程序
      ToastUtil.showToast("发生异常，已重启应用");
      // EnvironmentUtil.openApp(mContext, SplashActivity.class.getName());
      // Intent intent = new Intent(Intent.ACTION_MAIN);
      // intent.addCategory(Intent.CATEGORY_LAUNCHER);
      // ComponentName cn = new ComponentName(mContext.getPackageName(), className);
      // intent.setComponent(cn);
      // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      // mContext.startActivity(intent);

      String packageName = mContext.getPackageName();
      // Log.v("TEST","packageName:"+packageName);

      Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      mContext.startActivity(intent);

      android.os.Process.killProcess(android.os.Process.myPid());
    }
  }

  private static void openApp(Context context, String className) {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    ComponentName cn = new ComponentName(context.getPackageName(), className);
    intent.setComponent(cn);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity(intent);
  }

  /**
   * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
   *
   * @param ex
   * @return true:如果处理了该异常信息;否则返回false
   */
  private boolean handleException(Throwable ex) {
    if (ex == null) {
      return true;
    }
    // final String msg = ex.getLocalizedMessage();
    // 使用Toast来显示异常信息
    new Thread() {
      @Override
      public void run() {
        // Toast 显示需要出现在一个线程的消息队列中
        Looper.prepare();

        ToastUtil.showToast("程序异常，请重启应用");
        Looper.loop();
        // 程序出现异常，下次程序进入默认值更改为我的主页
        // SaveSharedPreference.setSharedPreferenceInt(mContext,
        // "lastSelect", R.id.rb_index_page);
      }
    }.start();
    // 收集设备信息
    collectCrashDeviceInfo(mContext);
    // 保存错误报告文件
    // saveCrashInfoToFile(ex);
    // 发送错误报告到服务器
    // sendCrashReportsToServer(mContext);
    return true;
  }

  /**
   * 收集程序崩溃的设备信息
   *
   * @param ctx
   */
  public void collectCrashDeviceInfo(Context ctx) {
    try {

      PackageManager pm = ctx.getPackageManager();

      PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
      if (pi != null) {

        mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);

        mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
      }
    } catch (Exception e) {
      Log.e(TAG, "Error while collect package info" + e);
    }
    // 使用反射来收集设备信息.在Build类中包含各种设备信息,
    Field[] fields = Build.class.getDeclaredFields();
    for (Field field : fields) {
      try {
        // setAccessible(boolean flag)
        // 将此对象的 accessible 标志设置为指示的布尔值。
        // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
        field.setAccessible(true);
        mDeviceCrashInfo.put(field.getName(), field.get(null));
        if (DEBUG) {
          Log.d(TAG, field.getName() + " : " + field.get(null));
        }
      } catch (Exception e) {
        Log.e(TAG, "Error while collect crash info" + e);
      }
    }
  }

  /**
   * 保存错误信息到文件中
   *
   * @param ex
   * @return
   */
  private String saveCrashInfoToFile(Throwable ex) {

    Writer info = new StringWriter();
    PrintWriter printWriter = new PrintWriter(info);
    // printStackTrace(PrintWriter s)
    // 将此 throwable 及其追踪输出到指定的 PrintWriter
    ex.printStackTrace(printWriter);

    // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
    Throwable cause = ex.getCause();
    while (cause != null) {
      cause.printStackTrace(printWriter);
      cause = cause.getCause();
    }

    // toString() 以字符串的形式返回该缓冲区的当前值。
    String result = info.toString();
    printWriter.close();
    mDeviceCrashInfo.put(STACK_TRACE, result);

    try {

      String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
      String fileName = "crash-" + timestamp + CRASH_REPORTER_EXTENSION;
      // 保存文件
      File directory = new File(EnvironmentUtil.Storage.getExternalCacheDir(mContext), "logs");
      if (!directory.exists()) {
        directory.mkdirs();
      }

      File file = new File(directory, fileName);
      FileOutputStream trace = new FileOutputStream(file);

      Log.e(TAG, new String(result.getBytes()));
      // 写入
      trace.write(result.getBytes());
      trace.flush();
      trace.close();
      return fileName;
    } catch (Exception e) {

      Log.e(TAG, e.toString());
    }
    return null;
  }

  /**
   * 把错误报告发送给服务器,包含新产生的和以前没发送的.
   *
   * @param ctx
   */
  private void sendCrashReportsToServer(Context ctx) {
    String[] crFiles = getCrashReportFiles(ctx);
    if (crFiles != null && crFiles.length > 0) {
      TreeSet<String> sortedFiles = new TreeSet<String>();
      sortedFiles.addAll(Arrays.asList(crFiles));

      for (String fileName : sortedFiles) {
        File cr = new File(ctx.getFilesDir(), fileName);
        postReport(cr);
        cr.delete();// 删除已发送的报告
      }
    }
  }

  /**
   * 获取错误报告文件名
   *
   * @param ctx
   * @return
   */
  private String[] getCrashReportFiles(Context ctx) {
    File filesDir = ctx.getFilesDir();
    // 实现FilenameFilter接口的类实例可用于过滤器文件名
    FilenameFilter filter = new FilenameFilter() {
      // accept(File dir, String name)
      // 测试指定文件是否应该包含在某一文件列表中。
      public boolean accept(File dir, String name) {
        return name.endsWith(CRASH_REPORTER_EXTENSION);
      }
    };
    // list(FilenameFilter filter)
    // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
    return filesDir.list(filter);
  }

  private void postReport(File file) {
    // 使用HTTP Post 发送错误报告到服务器
    // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
  }

  /**
   * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
   */
  public void sendPreviousReportsToServer() {
    sendCrashReportsToServer(mContext);

  }
}
