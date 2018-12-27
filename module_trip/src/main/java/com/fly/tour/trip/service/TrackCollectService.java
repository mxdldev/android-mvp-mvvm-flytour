package com.fly.tour.trip.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.fly.tour.trip.MainActivity;
import com.fly.tour.trip.R;
import com.fly.tour.trip.contract.TrackCollectEvent;
import com.fly.tour.trip.contract.TrackCollectListener;

import java.util.List;

/**
 * Description: <TrackCollectService><br>
 * Author:      gxl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TrackCollectService  extends Service {
    public static final String TAG = "MYTAG";
    private TripTrackCollection mTrackCollection;
    private Messenger mClientMessenger;

    private Handler serviceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TrackCollectEvent.INIT:
                    Log.v(TAG, "init...");
                    mClientMessenger = msg.replyTo;
                    break;
                case TrackCollectEvent.START:
                    Log.v(TAG, "start...");
                    if (mTrackCollection == null) {
                        mTrackCollection = TripTrackCollection.getInstance(TrackCollectService.this);
                    }
                    mTrackCollection.start();
                    break;
                case TrackCollectEvent.RESTART:
                    Log.v(TAG, "restart...");
                    mTrackCollection.restart();
                    break;
                case TrackCollectEvent.PAUSE:
                    Log.v(TAG, "pause...");
                    mTrackCollection.pause();
                    break;
                case TrackCollectEvent.STOP:
                    Log.v(TAG, "stop...");
                    if (mTrackCollection != null) {

                        float distance = 0f;
                        long totaltime = 0;
                        Bundle data = msg.getData();
                        if (data != null) {
                            distance = data.getFloat("distance");
                            totaltime = data.getLong("totaltime");
                        }
                        mTrackCollection.stop(totaltime, distance);
                    }
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(serviceHandler).getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTrackCollection = TripTrackCollection.getInstance(this);
        mTrackCollection.setCollectListener(new TrackCollectListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSucceed(List<LatLng> list) {
                if (mClientMessenger != null) {
                    Message obtain = Message.obtain();
                    try {
                        obtain.obj = list;
                        obtain.what = TrackCollectEvent.SUCC;
                        mClientMessenger.send(obtain);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinsh() {

            }
        });
        startFrontService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTrackCollection.destory();
        stopForeground(true);

    }
    // 发一个通知，使该服务成为一个前台服务，延长其服务的生命周期
    private void startFrontService() {
        Intent targetIntent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, targetIntent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("飞鸟出游");
        builder.setContentText("正在数据采集...");
        builder.setContentIntent(pIntent);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
            startForeground(100, notification);// 开启前台服务
        }
    }
}