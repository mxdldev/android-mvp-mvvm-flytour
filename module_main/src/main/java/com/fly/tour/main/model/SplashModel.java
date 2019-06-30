package com.fly.tour.main.model;

import android.content.Context;

import com.fly.tour.api.CommonService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.user.LoginDTO;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.main.contract.SplashContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <SplashModel><br>
 * Author:      gxl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class SplashModel extends BaseModel implements SplashContract.Model {
    private CommonService mCommonService;

    @Inject
    public SplashModel(Context context) {
        super(context);
        mCommonService = RetrofitManager.getInstance().getCommonService();
    }

    @Override
    public Observable<RespDTO<LoginDTO>> login(String username, String password) {
        return mCommonService.login(username, password)
                .compose(RxAdapter.bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}