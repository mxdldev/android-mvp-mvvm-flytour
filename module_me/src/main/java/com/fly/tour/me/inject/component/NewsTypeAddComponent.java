package com.fly.tour.me.inject.component;

import com.fly.tour.me.NewsTypeAddActivity;
import com.fly.tour.me.NewsTypeListActivity;
import com.fly.tour.me.inject.module.NewsTypeAddModule;
import com.fly.tour.me.inject.module.NewsTypeListModule;

import dagger.Component;

/**
 * Description: <NewsTypeListComponent><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Component(modules = {NewsTypeAddModule.class})
public interface NewsTypeAddComponent {
    void inject(NewsTypeAddActivity newsTypeAddActivity);
}
