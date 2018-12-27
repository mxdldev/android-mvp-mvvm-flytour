package com.fly.tour.main.entity;

/**
 * Description: <主频道类型><br>
 * Author:      gxl<br>
 * Date:        2018/12/12<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public enum MainChannel {
    TRIP(0,"TRIP"),DISCOVER(1,"DISCOVER"),ME(2,"ME");
    public int id;
    public String name;
    MainChannel(int id, String name){
        this.id = id;
        this.name = name;
    }
}
