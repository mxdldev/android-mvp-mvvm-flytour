package com.zjx.vcars.common.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

/**
 * Description: <单位转换工具类><br>
 * <ul>
 * <h1>基本功能：</h1>
 * <li>1.获取包名</li>
 * <li>2.获取当前应用的版本号</li>
 * <li>3.获取当前应用的版本名称</li>
 * <li>4.获取应用的名称</li>
 * <li>5.获取assets下资源</li>
 * <li>6.获取系统语言</li>
 * <li>7.获取assets下资源</li>
 * <li>8.app的是否在运行</li>
 * <li>9.app是否在前台运行</li>
 * <li>10.当前Activity是否在栈顶></li>
 * <li>11.启动应用</li>
 * <li>12.获取assets下资源</li>
 * <h1>存贮相关</h1>
 * <li>1.外部存储是否可读写</li>
 * <li>2.获取外部存储文件夹</li>
 * <li>3.获取外部存储路径</li>
 * <h1>APK相关操作</h1>
 * <li>1.安装APK</li>
 * <li>2.卸载APK</li>
 * <li>3.获得APK包名</li>
 * <li>4.判断是否已经安装</li>
 * </ul>
 * Author: gxl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */

public class EnvironmentUtil{
    private static final String TAG = "EnvironmentUtil";

    /**
     * 获取包名
     *
     * @return 包名
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取当前应用的版本号
     */
    public static int getAppVersionCode(Context context) {
        // 获取手机的包管理者
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(getPackageName(context), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前应用的版本名称
     */
    public static String getAppVersionName(Context context) {
        // 获取手机的包管理者
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(getPackageName(context), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            // 不可能发生.
            return "";
        }
    }

    /**
     * 获取应用的名称
     *
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(context), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        System.out.println(applicationName);
        return applicationName;
    }

    /**
     * 获取assets下资源
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getAssetsString(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * 获取系统语言
     *
     * @param context
     * @return
     * @param context
     * @return
     */
    public static String getSystemLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    /**
     * 当前app的是否在运行
     *
     * @return false: 说明app不在当前系统的栈中; true:当前app正处于用户使用状态(包含在Home在后台)
     */

    public boolean isAppRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(100);
        if (runningTaskInfos == null) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo taskInfo : runningTaskInfos)
            if (context.getPackageName().equals(taskInfo.baseActivity.getPackageName())) {
                return true;
            }
        return false;
    }

    /**
     * app是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前Activity是否排在栈顶
     *
     * @return
     */
    protected boolean isTopActivity(Context context, Class clazz) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null && !runningTaskInfos.isEmpty()
                && clazz.getName().equals(runningTaskInfos.get(0).topActivity.getClassName())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回app运行状态 1:程序在前台运行 2:程序在后台运行 3:程序未启动 注意：需要配置权限<uses-permission
     * android:name="android.permission.GET_TASKS" />
     */
    public static int getAppStatus(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);
        // 判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
            return 1;
        } else {
            // 判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(context.getPackageName())) {
                    return 2;
                }
            }
            return 3;// 栈里找不到，返回3
        }
    }

    /**
     * 启动应用
     *
     * @param context
     * @param className
     */
    public static void openApp(Context context, String className) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(context.getPackageName(), className);
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 存储信息
     */
    public static class Storage {

        /**
         * 外部存储是否可读写
         *
         * @return 可读写返回true, 否则返回false
         */
        public static boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        /**
         * 获取外部目录缓存路径
         *
         * @param context
         *            context
         * @return 外部存储换成路径
         */
        public static File getExternalCacheDir(Context context) {
            File file = null;
            if (SdkVersionUtil.hasFroyo()) {
                file = context.getExternalCacheDir();
            }

            if (file == null) {
                final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
                file = new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);

                file.mkdirs();

                if (file.isDirectory()) {
                    return file;
                }
            }
            return null;
        }

        /**
         * 获取缓存路径
         *
         * @param context
         *            context
         * @return 存储路径
         */
        public static String getCachePath(Context context) {
            File file = null;
            if (isExternalStorageWritable()) {
                file = getExternalCacheDir(context);
            }

            return (file != null) ? file.getAbsolutePath() : context.getCacheDir().getAbsolutePath();
        }

    }

    /**
     * APK相关操作
     */
    public static class Package {

        /**
         * 安装APK
         *
         * @param context
         * @param file
         */
        public static void installApp(Context context, File file) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }

        /**
         * 卸载APK
         *
         * @param context
         * @param packageName
         */
        public static void unInstallApp(Context context, String packageName) {
            Uri packageUri = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        /**
         * 获得APK包名
         *
         * @param context
         * @param apkFile
         * @return
         */
        public static String getApkFilePackage(Context context, File apkFile) {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkFile.getPath(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return info.applicationInfo.packageName;
            }
            return null;
        }

        /**
         * 判断是否已经安装
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean isAppInstalled(Context context, String packageName) {
            if (TextUtils.isEmpty(packageName))
                return false;
            try {
                context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            } catch (NameNotFoundException e) {
                return false;
            }
        }
    }
}