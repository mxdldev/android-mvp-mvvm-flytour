package com.fly.tour.trip.contract;

/**
 * Description: <轨迹数据采集协议><br>
 * Author: gxl<br>
 * Date: 2018/12/6<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public interface ITrackCollectionContract {


  // 开始收集
  void start();

  // 停止收集
  void stop();

  // 暂停收集
  void pause();

  //销毁
  void destory();

  //再次开始采集:点击暂定后再次开始
  void restart();
}
