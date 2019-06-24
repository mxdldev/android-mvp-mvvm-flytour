package com.fly.tour.trip.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.fly.tour.trip.contract.ITrackCollectionContract;
import com.fly.tour.trip.contract.TrackCollectListener;
import com.fly.tour.trip.entity.CollectServiceState;
import com.fly.tour.trip.entity.PointEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description: <TripTrackCollection><br>
 * Author:      mxdl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TripTrackCollection implements ITrackCollectionContract {
    private Context mContext;
    private static TripTrackCollection mTripTrackCollection;
    private AMapLocationClient mlocationClient;
    private AMapLocationListener mAMapLocationListener;
    private Vector<PointEntity> mLocations;
    private ScheduledExecutorService mDataBaseThread;// 入库线程
    private ExecutorService mVectorThread;// 入缓存线程
    private boolean isshowerror = true;
    private TrackCollectListener mCollectListener;
    private CollectServiceState currState;
    private int tripid;

    private TripTrackCollection(Context context) {
        mContext = context;
        // 初始缓存集合
        mLocations = new Vector<>();
        currState = CollectServiceState.INIT;
    }

    public static TripTrackCollection getInstance(Context context) {
        if (mTripTrackCollection == null) {
            synchronized (TripTrackCollection.class) {
                if (mTripTrackCollection == null) {
                    mTripTrackCollection = new TripTrackCollection(context);
                }
            }
        }
        return mTripTrackCollection;
    }

    // 开始采集数据
    @Override
    public void start() {
        Log.v("MYTAG", "start start...");
//        if (tripDao == null) {
//            tripDao = AppDB.getInstance(mContext).trip();
//        }
//        TripEntity mTrip = new TripEntity();
//        mTrip.status = TripDao.TRIP_STATUS_START;//开始状态
//        mTrip.createDateTime = new Date();
//        tripid = (int) tripDao.createTrip(mTrip);
        Log.v("MYTAG", "tripid:" + tripid);

        startLocation();
        startCollect();
        if (mCollectListener != null) {
            mCollectListener.onStart();
        }
    }

    // 开启定位服务
    private void startLocation() {
        Log.v("MYTAG", "startLocation start...");
        // 初始定位服务
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mContext);
        }
        // 初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 设置定位参数
        mlocationClient.setLocationOption(mLocationOption);

        mLocationOption.setOnceLocation(false);// 是否定位一次
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        // 设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation amapLocation) {
                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    // 定位成功回调信息，设置相关消息
                    // amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
                    // amapLocation.getLatitude();// 获取纬度
                    // amapLocation.getLongitude();// 获取经度
                    // amapLocation.getAccuracy();// 获取精度信息
                    if (mAMapLocationListener != null) {
                        mAMapLocationListener.onLocationChanged(amapLocation);
                    }
                    if (mVectorThread == null) {
                        mVectorThread = Executors.newSingleThreadExecutor();
                    }
                    Log.d("MYTAG", "lat:" + amapLocation.getLatitude() + "lon:" + amapLocation.getLongitude());
                    // 避免阻塞UI主线程，开启一个单独线程来存入内存
                    mVectorThread.execute(new Runnable() {
                        @Override
                        public void run() {
//                            PointEntity entity = new PointEntity();
//                            entity.tripId = tripid;
//                            entity.latitude = amapLocation.getLatitude();
//                            entity.longitude = amapLocation.getLongitude();
//                            entity.date = new Date(amapLocation.getTime());
//                            entity.direction = (int) amapLocation.getBearing();
//                            entity.satellites = amapLocation.getSatellites();
//                            entity.speed = amapLocation.getSpeed();
//                            LatLng latLng = AmapUtil.getInstance(mContext).convertAmpToGps(new LatLng(entity.latitude, entity.longitude));
//                            entity.latitude84 = latLng.latitude;
//                            entity.longitude84 = latLng.longitude;
//                            entity.altitude = amapLocation.getAltitude();
//                            entity.type = AmapUtil.getInstance(mContext).getLocationType(amapLocation.getLocationType());
                            //mLocations.add(entity);
                        }
                    });
                } else {
                    // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.d("MYTAG", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
                    if (isshowerror) {
                        isshowerror = false;
                        Toast.makeText(mContext, amapLocation.getErrorInfo(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mlocationClient.startLocation();

    }

    // 开启数据入库线程，五秒中入一次库
    private void startCollect() {
        Log.v("MYTAG", "startCollect start...");
        if (mDataBaseThread == null) {
            mDataBaseThread = Executors.newSingleThreadScheduledExecutor();
        }
        mDataBaseThread.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (mLocations != null && mLocations.size() > 0) {
                    // 取出缓存数据
                    PointEntity[] arrayList = new PointEntity[mLocations.size()];
                    mLocations.toArray(arrayList);
                    //TripDao tripDao = MyApplication.getInstance().getDatabase().trip();
                    //String string = UUID.randomUUID().toString();
                    Log.v("MYTAG", "save database count:" + arrayList.length);
                    //tripDao.insertTripPoint(arrayList);
                    //入库完毕要在地图把轨迹画出来
                    if (mCollectListener != null) {
                        Iterator<PointEntity> iterator = mLocations.iterator();
                        List<LatLng> latLngs = new ArrayList<>();
                        while (iterator.hasNext()) {
                            PointEntity next = iterator.next();
                            latLngs.add(new LatLng(next.latitude, next.longitude));
                        }
                        mCollectListener.onSucceed(latLngs);
                    }
                    // 取完之后清空数据
                    mLocations.clear();
                }


            }
        }, 1000 * 5, 1000 * 5, TimeUnit.MILLISECONDS);
    }

    //停止采集
    @Override
    public void stop() {
        Log.v("MYTAG", "stop start...");
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();//释放定位资源
            mlocationClient = null;
        }
        // 关闭Vector线程
        if (mVectorThread != null) {
            mVectorThread.shutdownNow();
            mVectorThread = null;
        }
        // 关闭SaveDabase线程
        if (mDataBaseThread != null) {
            mDataBaseThread.shutdownNow();
            mDataBaseThread = null;
        }
        // 定期任务关闭后，需要把最后的数据同步到数据库
        // 取出缓存数据
        PointEntity[] arrayList = new PointEntity[mLocations.size()];
        mLocations.toArray(arrayList);
//        if (tripDao != null) {
//            tripDao.insertTripPoint(arrayList);
//            Log.v("MYTAG", "save succ...");
//        }
        //修改trip的状态
//        if (tripDao != null) {
//            tripDao.updateTripStatus(TripDao.TRIP_STATUS_STOP, tripid);
//            tripid = -1;
//            Log.v("MYTAG", "update succ...");
//        }
        // 取完之后清空数据
        mLocations.clear();
        if (mCollectListener != null) {
            mCollectListener.onFinsh();
        }
    }

    @Override
    public void pause() {
        Log.v("MYTAG", "pause start...");
        //高德暂未提供暂停定位的操作，只能停止定位了，其他的工作也就停了
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient = null;
        }
        //修改trip的状态
//        if (tripDao != null) {
//            tripDao.updateTripStatus(TripDao.TRIP_STATUS_PAUSE, tripid);
//            Log.v("MYTAG", "update succ...");
//        }
    }

    @Override
    public void destory() {
        Log.v("MYTAG", "destory start...");
        stop();
    }

    @Override
    public void restart() {
        Log.v("MYTAG", "destory restart...");
        //把定位定位再次开启就行了，其他线程仍然继续服务
        startLocation();
        //修改trip的状态
//        if (tripDao != null) {
//            tripDao.updateTripStatus(TripDao.TRIP_STATUS_START, tripid);
//            Log.v("MYTAG", "update succ...");
//        }
    }

    public void setAMapLocationListener(AMapLocationListener AMapLocationListener) {
        mAMapLocationListener = AMapLocationListener;
    }

    public void setCollectListener(TrackCollectListener collectListener) {
        mCollectListener = collectListener;
    }

    public void stop(long totaltime, float distance) {
//        if (tripDao != null) {
//            tripDao.updateTripTotaltime(totaltime / 1000, (int) distance, tripid);
//            Log.v("MYTAG", "totaltime : " + totaltime);
//            Log.v("MYTAG", "distance : " + distance);
//        }
        stop();
    }
}
