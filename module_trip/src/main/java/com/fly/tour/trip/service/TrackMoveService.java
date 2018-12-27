package com.fly.tour.trip.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.fly.tour.trip.contract.TrackCollectEvent;
import com.fly.tour.trip.util.TrackMoveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <TrackMoveService><br>
 * Author:      gxl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TrackMoveService extends Thread {
    public static final String TAG = TrackMoveService.class.getName();
    private boolean suspend = false;
    private boolean stop = false;
    private Object lock = new Object();
    ;
    private LatLng startLatLng;
    private Handler moveCarHandler;
    private Handler showTrackHandler;

    @Override
    public void run() {
        Looper.prepare();
        moveCarHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                synchronized (lock) {
                    if (msg.obj != null && msg.obj instanceof ArrayList) {
                        List<LatLng> latLngList = (List<LatLng>) msg.obj;
                        moveTrack(latLngList);
                    }
                }

            }
        };
        Looper.loop();
    }

    public TrackMoveService(Handler handler) {
        super("TrackMoveService");
        this.showTrackHandler = handler;
    }

    // 轨迹运动核心方法
    private void moveTrack(List<LatLng> latLngList) {
        if (latLngList == null || latLngList.size() == 0 || latLngList.size() == 1) {
            return;
        }
        Log.v(TAG, "moveTrack start.........................................................");
        long startTime = System.currentTimeMillis();
        Log.v(TAG, "startTime:" + startTime);
        int step = TrackMoveUtil.getStep(latLngList);// 通过距离,计算轨迹动画运动步数
        Log.v(TAG, "move step:" + step);
        float distance = TrackMoveUtil.getDistance(latLngList);
        Log.v(TAG, "move distance:" + distance);
        //距离更新
        if (showTrackHandler != null) {
            Message obtain = Message.obtain();
            obtain.what = TrackCollectEvent.DISTANCE;
            obtain.obj = Float.valueOf(distance);
            showTrackHandler.sendMessage(obtain);
        }
        double time = TrackMoveUtil.getMoveTime(distance, step);// 通过距离,计算轨迹动画时间间隔
        time = 10;// 每走一步停止10毫秒
        Log.v(TAG, "move time:" + time);
        for (int i = 0; i < latLngList.size() - 1; i++) {
            // 暂停状态，线程停止了
            // if (suspend) {
            // try {
            // lock.wait();
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            // }
            if (stop) {
                break;
            }

            startLatLng = latLngList.get(i);
            LatLng endLatLng = latLngList.get(i + 1);

            //划线
            if (showTrackHandler != null) {
                Message obtain = Message.obtain();
                obtain.what = TrackCollectEvent.DRAW;
                obtain.obj = startLatLng;
                showTrackHandler.sendMessage(obtain);

            }

            // 非暂停状态地图才进行跟随移动
            if (!suspend && moveCarHandler != null) {
                Message message = Message.obtain();
                message.obj = startLatLng;
                message.what = TrackCollectEvent.MOVE;
                showTrackHandler.sendMessage(message);
            }

            double slope = TrackMoveUtil.getSlope(startLatLng, endLatLng);// 计算两点间的斜率
            double intercept = TrackMoveUtil.getInterception(slope, startLatLng);// 根据点和斜率算取截距
            boolean isReverse = (startLatLng.latitude > endLatLng.latitude);// 是不是正向的标示（向上设为正向）
            double xMoveDistance = isReverse ? TrackMoveUtil.getXMoveDistance(slope) : -1 * TrackMoveUtil.getXMoveDistance(slope);
            // 应该对经纬度同时处理
            double sleep = 0;
            int flag = 0;
            for (double j = startLatLng.latitude; !((j >= endLatLng.latitude) ^ isReverse); j = j - xMoveDistance) {
                // 非暂停状态地图才进行跟随移动
                if (!suspend && moveCarHandler != null) {
                    Message message = Message.obtain();
                    message.obj = startLatLng;
                    message.what = TrackCollectEvent.MOVE;
                    showTrackHandler.sendMessage(message);
                }
                // if (suspend) {
                // try {
                // lock.wait();
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }
                // }
                if (stop) {
                    break;
                }

                flag++;
                if (slope != Double.MAX_VALUE) {
                    startLatLng = new LatLng(j, (j - intercept) / slope);
                } else {
                    startLatLng = new LatLng(j, startLatLng.longitude);
                }
                //划线
                if (showTrackHandler != null) {
                    Message obtain = Message.obtain();
                    obtain.what = TrackCollectEvent.DRAW;
                    obtain.obj = startLatLng;
                    showTrackHandler.sendMessage(obtain);
                }

                // Log.v(TAG, "moveTrack mLatLngList
                // size:"+mLatLngList.size()+"......................................................");
                if (flag % 100 == 0) {

                }
                // 如果间隔时间小于1毫秒,则略过当前休眠,累加直到休眠时间到1毫秒:会损失精度
                if (time < 1) {
                    sleep += time;
                    if (sleep >= 1) {
                        SystemClock.sleep((long) sleep);
                        sleep = 0;
                    }
                } else {
                    SystemClock.sleep((long) time);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        Log.v(TAG, "endTime:" + endTime);
        Log.v(TAG, "run time:" + (endTime - startTime));
        Log.v(TAG, "moveTrack end.........................................................");
    }

    public void post(List<LatLng> list) {
        if (moveCarHandler != null && list != null && list.size() > 0) {
            Message obtain = Message.obtain();
            obtain.obj = list;
            moveCarHandler.sendMessage(obtain);
        }
    }

    public void post(Message message) {
        if (moveCarHandler != null && message != null) {
            Message obtain = Message.obtain();
            obtain.what = message.what;
            obtain.obj = message.obj;
            moveCarHandler.sendMessage(obtain);
        }
    }
}