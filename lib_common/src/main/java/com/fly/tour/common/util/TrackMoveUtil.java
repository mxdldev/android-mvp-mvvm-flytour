package com.zjx.vcars.common.util;

import android.text.TextUtils;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.trace.TraceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>轨迹平滑所需要的工具方法</h1> Date: 2016-10-27 Created by gxl
 */
public class TrackMoveUtil {
  private static double DISTANCE = 0.0001;

  /**
   * 根据两点算斜率
   */
  public static double getSlope(LatLng fromPoint, LatLng toPoint) {
    if (fromPoint == null || toPoint == null) {
      return 0;
    }
    if (toPoint.longitude == fromPoint.longitude) {
      return Double.MAX_VALUE;
    }
    double slope =
        ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
    return slope;

  }

  /**
   * 根据两点算取图标转的角度
   */
  public static double getAngle(LatLng fromPoint, LatLng toPoint) {
    if (fromPoint == null || toPoint == null) {
      return 0;
    }
    double slope = getSlope(fromPoint, toPoint);
    if (slope == Double.MAX_VALUE) {
      if (toPoint.latitude > fromPoint.latitude) {
        return 0;
      } else {
        return 180;
      }
    }
    float deltAngle = 0;
    if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
      deltAngle = 180;
    }
    double radio = Math.atan(slope);
    double angle = 180 * (radio / Math.PI) + deltAngle - 90;
    return angle;
  }

  /**
   * 根据点和斜率算取截距
   */
  public static double getInterception(double slope, LatLng point) {
    if (point == null) {
      return 0;
    }
    return point.latitude - slope * point.longitude;
  }

  /**
   * 计算x方向每次移动的距离
   */
  public static double getXMoveDistance(double slope) {
    if (slope == Double.MAX_VALUE) {
      return DISTANCE;
    }
    return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
  }

  /**
   * 根据轨迹线段计算小车走了多少步
   * 
   * @param latLngList
   * @return
   */
  public static int getStep(List<LatLng> latLngList) {
    int step = 0;
    if (latLngList != null && latLngList.size() > 1) {
      for (int i = 0; i < latLngList.size() - 1; i++) {
        try {
          LatLng startPoint = latLngList.get(i);
          LatLng endPoint = latLngList.get(i + 1);
          double slope = getSlope(startPoint, endPoint);
          // 是不是正向的标示（向上设为正向）
          boolean isReverse = (startPoint.latitude > endPoint.latitude);
          double xMoveDistance = isReverse ? getXMoveDistance(slope) : -1 * getXMoveDistance(slope);
          // 应该对经纬度同时处理
          for (double j = startPoint.latitude; !((j >= endPoint.latitude) ^ isReverse); j =
              j - xMoveDistance) {
            step++;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    }
    return step;
  }

  /**
   * 根据总距离和步数计算运动时间
   * 
   * @param distance
   * @param step
   * @return
   */
  public static double getMoveTime(float distance, int step) {
    double timeInterval = 0;
    if (distance > 0) {
      float totalDistance = distance * 1000;
      if (totalDistance <= 500) {
        timeInterval = 1000.0 / step;
      } else if (totalDistance > 500 && totalDistance <= 7500) {
        timeInterval = 2.0 * totalDistance / step;
      } else {
        timeInterval = 15000.0 / step;
      }
    }
    return timeInterval;
  }

  /**
   * 根据轨迹点集合计算总距离
   * 
   * @param latLngList
   * @return
   */
  public static float getDistance(List<LatLng> latLngList) {
    float distance = 0;
    if (latLngList != null && latLngList.size() > 1) {
      for (int i = 0; i < latLngList.size() - 1; i++) {
        try {
          distance += AMapUtils.calculateLineDistance(latLngList.get(i), latLngList.get(i + 1));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return distance;
  }

  // latitude - 地点的纬度，在-90 与90 之间的double 型数值。
  // longitude - 地点的经度，在-180 与180 之间的double 型数值。
  /**
   * 根据一个经纬度字符串求一个经纬度集合a|b|c|d;
   * 
   * @param latlonStr
   * @return
   */
  public static List<LatLng> getListLatLng(String latlonStr) {
    if (!TextUtils.isEmpty(latlonStr)) {
      String[] trackArr = latlonStr.split("\\|");
      if (trackArr != null && trackArr.length > 0) {
        List<LatLng> latLngList = new ArrayList<LatLng>();
        for (int i = 0; i < trackArr.length - 1; i = i + 2) {
          try {
            String lat = trackArr[i + 1];
            String lng = trackArr[i];
            // Logger.v(TAG,"trackArr index:" + i);
            // Logger.v(TAG,"trackArr lat:" + lat);
            // Logger.v(TAG,"trackArr lng:" + lng);
            if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)) {
              Double dLat = Double.valueOf(lat);
              Double dLng = Double.valueOf(lng);
              if (dLat >= -90 && dLat <= 90 && dLng >= -180 && dLng <= 180
                  && !(dLat == 0 && dLng == 0)) {
                LatLng latLng = new LatLng(dLat, dLng);
                latLngList.add(latLng);
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        return latLngList;
      }
    }
    return null;
  }

  public static List<TraceLocation> getListTraceLocation(String latlonStr) {
    if (!TextUtils.isEmpty(latlonStr)) {
      String[] trackArr = latlonStr.split("\\|");
      if (trackArr != null && trackArr.length > 0) {
        List<TraceLocation> latLngList = new ArrayList<TraceLocation>();
        for (int i = 0; i < trackArr.length - 1; i = i + 2) {
          try {
            String lat = trackArr[i + 1];
            String lng = trackArr[i];
            // Logger.v(TAG,"trackArr index:" + i);
            // Logger.v(TAG,"trackArr lat:" + lat);
            // Logger.v(TAG,"trackArr lng:" + lng);
            if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)) {
              Double dLat = Double.valueOf(lat);
              Double dLng = Double.valueOf(lng);
              if (dLat >= -90 && dLat <= 90 && dLng >= -180 && dLng <= 180) {
                TraceLocation latLng = new TraceLocation();
                latLng.setLatitude(dLat);
                latLng.setLongitude(dLng);
                latLngList.add(latLng);
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        return latLngList;
      }
    }
    return null;
  }

  public static List<TraceLocation> getListTraceLocation(List<LatLng> latlngList) {
    if (latlngList != null && latlngList.size() > 0) {
      List<TraceLocation> traceLocationList = new ArrayList<TraceLocation>();
      for (int i = 0; i < latlngList.size(); i++) {
        LatLng latLng = latlngList.get(i);
        TraceLocation traceLocation = new TraceLocation();
        traceLocation.setLatitude(latLng.latitude);
        traceLocation.setLongitude(latLng.longitude);
        traceLocationList.add(traceLocation);
      }
      return traceLocationList;
    }
    return null;
  }
}
