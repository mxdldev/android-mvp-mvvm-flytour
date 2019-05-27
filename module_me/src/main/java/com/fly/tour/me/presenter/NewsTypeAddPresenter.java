package com.fly.tour.me.presenter;

import android.content.Context;

import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.me.contract.NewsTypeAddContract;
import com.fly.tour.me.model.NewsTypeAddModel;

/**
 * Description: <NewsTypeAddPresenter><br>
 * Author:      gxl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddPresenter extends BasePresenter<NewsTypeAddModel,NewsTypeAddContract.View> implements NewsTypeAddContract.Presenter {
    public NewsTypeAddPresenter(Context context) {
        super(context);
    }

    @Override
    public NewsTypeAddModel initModel() {
        return new NewsTypeAddModel(mContext);
    }

    @Override
    public void addNewsType(String typename) {
        boolean b = mModel.addNewsType(typename);
        if(b){
            ToastUtil.showToast("添加成功");
            mView.finishActivity();
        }else{
            ToastUtil.showToast("添加失败");
        }
    }
}
