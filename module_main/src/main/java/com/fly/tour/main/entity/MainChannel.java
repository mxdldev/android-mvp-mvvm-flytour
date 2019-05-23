package com.fly.tour.main.entity;

/**
 * Description: <主频道类型><br>
 * Author:      gxl<br>
 * Date:        2018/12/12<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public enum MainChannel {
    NEWS(0,"NEWS"), FIND(1,"FIND"),ME(2,"ME");
    public int id;
    public String name;
    MainChannel(int id, String name){
        this.id = id;
        this.name = name;
    }
}
