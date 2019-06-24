package com.fly.tour.me.inject.component;

import com.fly.tour.me.NewsDetailAddActivity;
import com.fly.tour.me.NewsTypeAddActivity;
import com.fly.tour.me.inject.module.NewsDetailAddModule;
import com.fly.tour.me.inject.module.NewsTypeAddModule;

import dagger.Component;

/**
 * Description: <NewsTypeListComponent><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Component(modules = {NewsDetailAddModule.class})
public interface NewsDetailAddComponent {
    void inject(NewsDetailAddActivity newsDetailAddActivity);
}
