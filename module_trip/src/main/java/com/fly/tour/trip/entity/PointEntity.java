package com.fly.tour.trip.entity;

import java.util.Date;
public class PointEntity {
    public long pointId;

    /**
     * 对应行程ID
     */
    public int recordId;

    /**
     * 定位标记 0-无定位，1-GPS定位，2-综合定位
     */
    public int type;
    /**
     * 有效星数
     */
    public int satellites;
    /**
     * 方向
     */
    public int direction;

    /**
     * 经度84坐标GPS原始坐标 LONG 4  单位：0.000001度，正数表示北纬（N），负数表示南纬（S），默认84坐标，无效定位取值0x00000000
     */
    public double longitude84;
    /**
     * 纬度84坐标GPS原始坐标 LONG 4  单位：0.000001度，正数表示东经（E），负数表示西经（W），默认84坐标，无效定位取值0x00000000
     */
    public double latitude84;

    /**
     * 经度 偏移计算后的火星坐标 LONG 4  单位：0.000001度，正数表示北纬（N），负数表示南纬（S），默认84坐标，无效定位取值0x00000000
     */
    public double longitude;
    /**
     * 纬度 偏移计算后的火星坐标 LONG 4  单位：0.000001度，正数表示东经（E），负数表示西经（W），默认84坐标，无效定位取值0x00000000
     */
    public double latitude;

    /**
     * 海拔高度(单位：米) 默认值
     */
    public double altitude;
    /**
     * 速度 单位：米/秒
     */
    public float speed;

    /**
     * 定位时间 LocalDateTime
     */
    public Date date;

    @Override
    public String toString() {
        return "PointEntity{" +
                "pointId=" + pointId +
                ", record_id=" + recordId +
                ", type=" + type +
                ", satellites=" + satellites +
                ", direction=" + direction +
                ", longitude84=" + longitude84 +
                ", latitude84=" + latitude84 +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", date=" + date +
                '}';
    }
}
