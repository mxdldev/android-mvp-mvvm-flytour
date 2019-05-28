package com.fly.tour.news.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.manager.NewsDBManager;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.trip.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <MainNewsFragment><br>
 * Author:      gxl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainNewsFragment extends BaseFragment {
    private List<String> titles = new ArrayList<>();
    private List<NewsListFragment> mArrayList = new ArrayList<NewsListFragment>() {
        {
            List<NewsType> listNewsType = NewsDBManager.getInstance(getContext()).getListNewsType();
            if(listNewsType != null && listNewsType.size() > 0){
                for(int i = 0; i < listNewsType.size();i++){
                    NewsType newsType = listNewsType.get(i);
                    add(NewsListFragment.newInstance(newsType));
                    titles.add(newsType.getTypename());
                }
            }
        }
    };
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static MainNewsFragment newInstance() {
        return new MainNewsFragment();
    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_news_main;
    }
    @Override
    public void initView(View view) {
        mViewPager = view.findViewById(R.id.pager_tour);

        mTabLayout = view.findViewById(R.id.layout_tour);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mViewPager.setOffscreenPageLimit(mArrayList.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mArrayList.get(position);
            }

            @Override
            public int getCount() {
                return mArrayList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mArrayList.get(tab.getPosition()).autoLoadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }
}
