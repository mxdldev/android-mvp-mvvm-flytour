/*--------------------------------------------------
 * Copyright (C) 2015 The Android YeswaySDK Project
 *                http://www.yesway.cn/
 * 创建时间：2015/12/21
 * 内容说明：Activity操作类
 * 
 * 编号		日期			担当者		内容                  
 * -------------------------------------------------
 *
 * 
 *--------------------------------------------------*/
package com.fly.tour.common.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <Activity管理器><br>
 * Author: gxl<br>
 * Date: 2018/6/6<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class ActivityCollectManager {

  private static List<Activity> mActivityList = new ArrayList<Activity>();

  public static void addActivity(Activity activity) {
    mActivityList.add(activity);
  }

  public static void removeActivity(Activity activity) {
    mActivityList.remove(activity);
  }

  public static void finishAllActivity() {
    for (Activity activity : mActivityList) {
      if (activity.isFinishing() == false) {
        activity.finish();
      }
    }
  }
}
