package com.fly.tour.main.presenter;

import android.content.Context;
import android.util.Log;

import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.config.API;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.ExceptionHandler;
import com.fly.tour.api.user.LoginDTO;
import com.fly.tour.common.mvp.BasePresenter;
import com.fly.tour.main.contract.SplashContract;
import com.fly.tour.main.model.SplashModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: <SplashPresenter><br>
 * Author:      gxl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class SplashPresenter extends BasePresenter<SplashModel, SplashContract.View> implements SplashContract.Presenter{
    public static String TAG = SplashPresenter.class.getSimpleName();
    @Inject
    public SplashPresenter(Context context, SplashContract.View view, SplashModel model) {
        super(context, view, model);
    }

    @Override
    public void login() {
        mModel.login("gxl","123456").subscribe(new Observer<RespDTO<LoginDTO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RespDTO<LoginDTO> loginDTORespDTO) {
                if(loginDTORespDTO.code == ExceptionHandler.APP_ERROR.SUCC){
                    Log.v(TAG,"tolen:"+loginDTORespDTO.data.getToken());
                    RetrofitManager.getInstance().TOKEN = "Bearer "+loginDTORespDTO.data.getToken();
                }else{
                    Log.v(TAG,"error:"+loginDTORespDTO.error);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.startMainActivity();
            }

            @Override
            public void onComplete() {
                mView.startMainActivity();
            }
        });
    }
}