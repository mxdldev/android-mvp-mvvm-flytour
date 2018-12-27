package com.fly.tour.common.base;

public abstract class BasePresenter<V> {
  public V mView;

  public void attach(V mView) {
    this.mView = mView;
  }

  public void dettach() {
    mView = null;
  }
}
