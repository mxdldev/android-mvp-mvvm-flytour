package com.fly.tour.trip.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.util.NumberUtils;
import com.fly.tour.trip.R;
import com.fly.tour.trip.contract.TrackCollectEvent;
import com.fly.tour.trip.service.TrackCollectService;
import com.fly.tour.trip.service.TrackMoveService;
import com.fly.tour.trip.view.RecordToolBar;
import com.fly.tour.trip.view.RecordTopBar;

import java.util.ArrayList;
import java.util.List;


/**
 * Description: <出游记录><br>
 * Author:      mxdl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TripRecordFragment extends BaseFragment implements RecordToolBar.RecordToolBarClickListener {
    public static final String TAG = TripRecordFragment.class.getName();
    RecordTopBar mRecordTopBar;
    ImageButton mBtnStart;
    RecordToolBar mRecordToolBar;
    TextureMapView mMapView;
    private Messenger mServiceMessenger;
    private TrackMoveService mTrackMoveHandlerThread;
    private Polyline mMovePolyline;// 移动轨迹线
    private List<LatLng> mLatLngList = new ArrayList<LatLng>();// 轨迹总线集合
    private AMap mMap;
    private Marker mStartMarker;// 起点
    private Marker mLocationMarker;// 定位点
    private boolean isLocationFirstMove = true;// 是否是第一次移动
    private Float distance = 0f;//总距离(米)
    long startTime = 0;//开始时间（毫秒）
    long totalTime = 0;//总时间（毫秒）


    public static TripRecordFragment newInstance() {
        return new TripRecordFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_trip_record, container, false);
        mRecordTopBar = view.findViewById(R.id.view_topbar);
        mBtnStart = view.findViewById(R.id.btn_start);
        mRecordToolBar = view.findViewById(R.id.view_toolbar);
        mMapView = view.findViewById(R.id.map);
        mRecordToolBar.setListener(this);
        mMap = mMapView.getMap();
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartClick();
            }
        });
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        startTrackCollectService();
        startTrackMoveService();
    }

    private Handler mClientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TrackCollectEvent.SUCC:
                    if (mTrackMoveHandlerThread != null) {
                        Log.v("MYTAG", "receive data...");
                        mTrackMoveHandlerThread.post(msg);
                    }
                    break;
                case TrackCollectEvent.MOVE: // 小车挪动
                    if (msg.obj instanceof LatLng) {
                        final LatLng latLng = (LatLng) msg.obj;
                        if (mStartMarker == null) {
                            mStartMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_start)).anchor(0.5f, 0.5f));
                        }

                        if (mLocationMarker == null) {
                            mLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_my)).anchor(0.5f, 0.5f));
                        } else {
                            mLocationMarker.setPosition(latLng);
                        }

                        Log.v("MYTAG", "car move start...");
                        // 第一次小车挪动的时候给一个默认级别14，以后移动就不管了，避免第一次地图级别太大而导致的显示不合理问题
                        if (isLocationFirstMove) {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                                }
                            });
                            isLocationFirstMove = false;
                        } else {
                            mMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng));
                        }
                    }
                    break;

                case TrackCollectEvent.DRAW:
                    if (msg.obj instanceof LatLng) {
                        Log.v("MYTAG", "draw line start...");
                        mLatLngList.add((LatLng) msg.obj);
                        if (mMovePolyline == null) {
                            mMovePolyline = mMap.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.ic_tour_track)));// 地图上增加一条默认的轨迹线
                        }
                        mMovePolyline.setPoints(mLatLngList);
                    }
                    break;
                case TrackCollectEvent.DISTANCE:
                    if (msg.obj instanceof Float) {
                        Log.v("MYTAG", "distance update start...");
                        Float value = (Float) msg.obj;
                        Log.v("MYTAG", "distance：" + value);
                        distance += value;
                        mRecordTopBar.setTxtDistance(NumberUtils.keepPrecision(distance / 1000, 2) + "");
                    }
                    break;
            }
        }
    };
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = totalTime + (System.currentTimeMillis() - startTime);
            int seconds = (int) (millis / 1000);
            int hour = seconds / (60 * 60);
            seconds = seconds % (60 * 60);

            int minutes = seconds / 60;
            seconds = seconds % 60;

            mRecordTopBar.setTxtTime(String.format("%02d:%02d:%02d", hour, minutes, seconds));

            timerHandler.postDelayed(this, 1000);
        }
    };

    //开启轨迹显示服务
    private void startTrackMoveService() {
        mTrackMoveHandlerThread = new TrackMoveService(mClientHandler);
        mTrackMoveHandlerThread.start();
    }

    //启动轨迹信息收集服务
    private void startTrackCollectService() {
        Intent intent = new Intent(getContext(), TrackCollectService.class);
        getContext().startService(intent);
        getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //mTrackCollection = (TrackCollectionContract) service;
                mServiceMessenger = new Messenger(service);
                Message message = Message.obtain();
                message.replyTo = new Messenger(mClientHandler);
                try {
                    mServiceMessenger.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    void onStartClick() {
        if (mServiceMessenger != null) {
            sendMsgToService(TrackCollectEvent.START);
            mBtnStart.setVisibility(View.GONE);
            mRecordToolBar.setVisibility(View.VISIBLE);

            distance = 0f;//距离清0
            totalTime = 0;//时间清0
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }


    @Override
    public void onPauseClick(View view, boolean checked) {
        //规则：true ：暂停采集；false：重新开始采集
        if (checked) {
            totalTime += System.currentTimeMillis() - startTime;
            timerHandler.removeCallbacks(timerRunnable);
            sendMsgToService(TrackCollectEvent.PAUSE);
        } else {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);

            sendMsgToService(TrackCollectEvent.RESTART);
        }
    }

    private void sendMsgToService(int msgtype) {
        if (mServiceMessenger != null) {
            Message obtain = Message.obtain();
            obtain.what = msgtype;
            //如果停止了，把时间和距离同步到数据库
            if (TrackCollectEvent.STOP == msgtype) {
                Bundle data = new Bundle();
                data.putLong("totaltime", totalTime);
                data.putFloat("distance", distance);
                obtain.setData(data);
            }
            try {
                mServiceMessenger.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStopClick(View view) {
        //UI更新
        //停止计时
        totalTime += System.currentTimeMillis() - startTime;
        timerHandler.removeCallbacks(timerRunnable);
        mRecordTopBar.setDistanceAndTime("0.0", "00:00");

        //时间，距离同步到库里

        //停止采集服务
        sendMsgToService(TrackCollectEvent.STOP);
        mRecordToolBar.setVisibility(View.GONE);
        mBtnStart.setVisibility(View.VISIBLE);
        //清除地图上的所有数据
        mMap.clear();
        //轨迹点清空
        mLatLngList.clear();
        mMovePolyline = null;
        mStartMarker = null;
        mLocationMarker = null;
    }

    @Override
    public void onVideoClick(View view) {

    }

    @Override
    public void onSelectPhotoClick(View view) {

    }

    @Override
    public void onTakePhotoClick(View view) {

    }

}
