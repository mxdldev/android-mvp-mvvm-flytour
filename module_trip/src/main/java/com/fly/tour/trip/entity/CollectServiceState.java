package com.fly.tour.trip.entity;

/**
 * Description: <采集服务状态><br>
 * Author:      mxdl<br>
 * Date:        2018/12/14<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public enum CollectServiceState {
    INIT(0,"停止"),START(1,"开始"),PAUSE(2,"暂停");
    int id;
    String name;
    CollectServiceState(int id, String name){
        this.id = id;
        this.name = name;
    }
}
