package com.fly.tour.common.mvp;
/**
 * Description: <Presenter基础协议><br>
 * Author:      gxl<br>
 * Date:        2018/12/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BasePresenter<V> implements BaseContract.Presenter<V> {
  public V mView;

  @Override
  public void attach(V view) {
    this.mView = view;
  }

  @Override
  public void dettach() {
    mView = null;
  }
}
