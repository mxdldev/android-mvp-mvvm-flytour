package com.fly.tour.common.mvp;

public interface BaseView extends BaseContract.View {

	void showLoading();

	void hideLoading();

	void showErrNetWork();
}
