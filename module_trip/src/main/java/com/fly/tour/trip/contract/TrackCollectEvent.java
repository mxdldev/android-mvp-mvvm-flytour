package com.fly.tour.trip.contract;

import android.support.annotation.IntDef;

/**
 * Description: <轨迹采集事件类型><br>
 * Author:      gxl<br>
 * Date:        2018/12/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface TrackCollectEvent {
    int INIT = 0;//初始化事件
    int START = 1;//开始采集事件
    int RESTART = 2;//重新开始采集事件
    int PAUSE = 3;//暂停采集事件
    int STOP = 4;//停止采集事件
    int SUCC = 5;//每次入库成功事件
    int MOVE = 6;//轨迹移动事件
    int DRAW = 7;//画线事件
    int DISTANCE = 8;//画线事件

    @IntDef({START, RESTART, PAUSE,STOP})
    @interface TrackStatus {
    }
}
