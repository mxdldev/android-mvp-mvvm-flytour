package com.fly.tour.trip.contract;

import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * Description: <轨迹数据采集监听器><br>
 * Author:      mxdl<br>
 * Date:        2018/12/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface TrackCollectListener {
    void onStart();//开始采集的回调
    void onSucceed(List<LatLng> list);//入库成功回调
    void onFinsh();//停止采集的回调

}
