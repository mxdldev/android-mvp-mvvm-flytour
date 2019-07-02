package com.fly.tour.main.mvvm.model;

import android.app.Application;
import android.content.Context;

import com.fly.tour.api.CommonService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.user.LoginDTO;
import com.fly.tour.common.mvvm.model.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <SplashModel><br>
 * Author:      mxdl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class SplashModel extends BaseModel {
    private CommonService mCommonService;

    public SplashModel(Application application) {
        super(application);
        mCommonService = RetrofitManager.getInstance().getCommonService();
    }


    public Observable<RespDTO<LoginDTO>> login(String username, String password) {
        return mCommonService.login(username, password)
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}