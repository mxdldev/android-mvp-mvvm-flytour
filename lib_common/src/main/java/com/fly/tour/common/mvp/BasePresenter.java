package com.fly.tour.common.mvp;

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
