package com.fly.tour.common.util;

/**
 * Description: <SPUtils><br>
 * Author: gxl<br>
 * Date: 2018/6/19<br>
 * Version: V1.0.0<br>
 * Update: <br>
 * <ul>
 * <li>1.缓存数据</li>
 * <li>2.获取数据</li>
 * <li>3.删除数据</li>
 * <li>4.清空数据</li>
 * </ul>
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
  /**
   * 保存在手机里面的文件名
   */
  public static final String FILE_NAME = "fly_tour_data";

  /**
   * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
   *
   * @param context
   * @param key
   * @param object
   */
  public static void put(Context context, String key, Object object) {

    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();

    if (object instanceof String) {
      editor.putString(key, (String) object);
    } else if (object instanceof Integer) {
      editor.putInt(key, (Integer) object);
    } else if (object instanceof Boolean) {
      editor.putBoolean(key, (Boolean) object);
    } else if (object instanceof Float) {
      editor.putFloat(key, (Float) object);
    } else if (object instanceof Long) {
      editor.putLong(key, (Long) object);
    } else {
      editor.putString(key, object.toString());
    }

    SharedPreferencesCompat.apply(editor);
  }

  /**
   * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
   *
   * @param context
   * @param key
   * @param defaultObject
   * @return
   */
  public static Object get(Context context, String key, Object defaultObject) {
    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    if (defaultObject instanceof String) {
      return sp.getString(key, (String) defaultObject);
    } else if (defaultObject instanceof Integer) {
      return sp.getInt(key, (Integer) defaultObject);
    } else if (defaultObject instanceof Boolean) {
      return sp.getBoolean(key, (Boolean) defaultObject);
    } else if (defaultObject instanceof Float) {
      return sp.getFloat(key, (Float) defaultObject);
    } else if (defaultObject instanceof Long) {
      return sp.getLong(key, (Long) defaultObject);
    }

    return null;
  }

  /**
   * 移除某个key值已经对应的值
   *
   * @param context
   * @param key
   */
  public static void remove(Context context, String key) {
    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.remove(key);
    SharedPreferencesCompat.apply(editor);
  }

  /**
   * 清除所有数据
   *
   * @param context
   */
  public static void clear(Context context) {
    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.clear();
    SharedPreferencesCompat.apply(editor);
  }

  /**
   * 查询某个key是否已经存在
   *
   * @param context
   * @param key
   * @return
   */
  public static boolean contains(Context context, String key) {
    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    return sp.contains(key);
  }

  /**
   * 返回所有的键值对
   *
   * @param context
   * @return
   */
  public static Map<String, ?> getAll(Context context) {
    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    return sp.getAll();
  }

  /**
   * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
   *
   * @author zhy
   */
  private static class SharedPreferencesCompat {
    private static final Method sApplyMethod = findApplyMethod();

    /**
     * 反射查找apply的方法
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Method findApplyMethod() {
      try {
        Class clz = SharedPreferences.Editor.class;
        return clz.getMethod("apply");
      } catch (NoSuchMethodException e) {}

      return null;
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    public static void apply(SharedPreferences.Editor editor) {
      try {
        if (sApplyMethod != null) {
          sApplyMethod.invoke(editor);
          return;
        }
      } catch (IllegalArgumentException e) {} catch (IllegalAccessException e) {} catch (InvocationTargetException e) {}
      editor.commit();
    }
  }

}
