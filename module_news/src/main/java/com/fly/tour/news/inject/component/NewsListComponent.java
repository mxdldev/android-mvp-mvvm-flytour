package com.fly.tour.news.inject.component;

import com.fly.tour.news.NewsDetailActivity;
import com.fly.tour.news.fragment.NewsListFragment;
import com.fly.tour.news.inject.module.NewsDetailModule;
import com.fly.tour.news.inject.module.NewsListModule;

import dagger.Component;

/**
 * Description: <NewsDetailComponent><br>
 * Author:      mxdl<br>
 * Date:        2019/5/31<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
@Component(modules = {NewsListModule.class})
public interface NewsListComponent {
    void inject(NewsListFragment listFragment);
}
