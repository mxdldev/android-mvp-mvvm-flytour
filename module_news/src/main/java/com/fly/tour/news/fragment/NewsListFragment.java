package com.fly.tour.news.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.event.KeyCode;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.trip.R;

/**
 * Description: <人工智能><br>
 * Author:      gxl<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListFragment extends BaseFragment {

    private NewsType mNewsType;

    public static NewsListFragment newInstance(NewsType newsType) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putParcelable(KeyCode.News.NEWS_TYPE,newsType);
        newsListFragment.setArguments(args);

        return newsListFragment;
    }
    public String getFragmentTitle(){
        if(mNewsType != null){

        }
        return null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsType = getArguments().getParcelable(KeyCode.News.NEWS_TYPE);
    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getToolbarTitle() {
        return null;
    }
}
