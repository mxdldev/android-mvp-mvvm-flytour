package com.fly.tour.common.mvp;
/**
 * Description: <View基础协议><br>
 * Author:      gxl<br>
 * Date:        2018/12/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface BaseView extends BaseContract.View {
	//显示刷新菊花
	void showRefreshView();
	//隐藏刷新菊花
	void hideRefreshView();
	//自动刷新
	void autoRefresh();
	//自动加载数据
	void autoLoadData();
}
