package com.fly.tour.main.inject.component;

import com.fly.tour.main.SplashActivity;
import com.fly.tour.main.inject.module.SplashModule;

import dagger.Component;

/**
 * Description: <SplashComponent><br>
 * Author:      gxl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Component(modules = {SplashModule.class})
public interface SplashComponent {
    void inject(SplashActivity activity);
}
