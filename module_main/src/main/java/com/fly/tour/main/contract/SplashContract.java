package com.fly.tour.main.contract;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.user.LoginDTO;
import com.fly.tour.common.mvp.BaseView;

import io.reactivex.Observable;


/**
 * Description: <SplashContract><br>
 * Author:      gxl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface SplashContract {
    interface Presenter{
        void login();
    }
    interface View extends BaseView {
        void startMainActivity();
    }
    interface Model{
        Observable<RespDTO<LoginDTO>> login(String username, String password);
    }
}