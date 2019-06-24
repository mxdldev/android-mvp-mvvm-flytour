package com.fly.tour.news.inject.component;

import com.fly.tour.news.NewsDetailActivity;
import com.fly.tour.news.contract.NewsTypeContract;
import com.fly.tour.news.fragment.MainNewsFragment;
import com.fly.tour.news.inject.module.NewsDetailModule;
import com.fly.tour.news.inject.module.NewsTypeModule;

import dagger.Component;

/**
 * Description: <NewsDetailComponent><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Component(modules = {NewsTypeModule.class})
public interface NewsTypeComponent {
    void inject(MainNewsFragment mainNewsFragment);
}
