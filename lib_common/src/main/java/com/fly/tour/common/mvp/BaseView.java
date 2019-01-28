package com.fly.tour.common.mvp;
/**
 * Description: <View基础协议><br>
 * Author:      gxl<br>
 * Date:        2018/12/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface BaseView extends BaseContract.View {

	void showLoading();

	void hideLoading();

	void showErrNetWork();
}
