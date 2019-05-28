package com.fly.tour.news.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.manager.ChannelManager;
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
            List<NewsType> listNewsType = ChannelManager.getInstance(getContext()).getListNewsType();
            if(listNewsType != null && listNewsType.size() > 0){
                for(int i = 0; i < listNewsType.size();i++){
                    NewsType newsType = listNewsType.get(i);
                    add(NewsListFragment.newInstance(newsType));
                    titles.add(newsType.getTypename());
                }
            }
        }
    };

    public static MainNewsFragment newInstance() {
        return new MainNewsFragment();
    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_news_main;
    }

    @Override
    public void initView(View view) {
        ViewPager viewPager = view.findViewById(R.id.pager_tour);
        TabLayout tabLayout = view.findViewById(R.id.layout_tour);

        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
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
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initData() {

    }

    @Override
    public String getToolbarTitle() {
        return null;
    }
}
