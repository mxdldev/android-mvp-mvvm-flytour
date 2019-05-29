package com.fly.tour.news.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.common.event.me.NewsTypeCrudEvent;
import com.fly.tour.common.manager.NewsDBManager;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.trip.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Description: <MainNewsFragment><br>
 * Author:      gxl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainNewsFragment extends BaseFragment {
    private List<String> titles = new ArrayList<>();
    private List<NewsListFragment> mListFragments = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NewsFragmentAdapter mNewsFragmentAdapter;
    private boolean mIsfresh;

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
        initNewsListFragment();
        //mViewPager.setOffscreenPageLimit(mListFragments.size());
        mNewsFragmentAdapter = new NewsFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mNewsFragmentAdapter);
        mNewsFragmentAdapter.refreshViewPager(mListFragments);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mListFragments.get(tab.getPosition()).autoLoadData();
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

    class NewsFragmentAdapter extends FragmentPagerAdapter {
        public FragmentManager mFragmentManager;
        List<NewsListFragment> pages;

        public NewsFragmentAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }


        @Override
        public Fragment getItem(int position) {
            if (pages != null) {
                return pages.get(position);
            }
            return null;
        }


        @Override
        public int getCount() {
            return pages != null ? pages.size() : 0;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        public void refreshViewPager(List<NewsListFragment> listFragments) {
            if (pages != null && pages.size() > 0) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for (NewsListFragment fragment : pages) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
            }
            pages = listFragments;
            notifyDataSetChanged();

            mViewPager.setCurrentItem(0);
        }
    }

    private void initNewsListFragment() {
        List<NewsType> listNewsType = NewsDBManager.getInstance(getContext()).getListNewsType();
        KLog.v("MYTAG", "initNewsListFragment start..." + listNewsType.toString());
        mListFragments.clear();
        titles.clear();
        if (listNewsType != null && listNewsType.size() > 0) {
            for (int i = 0; i < listNewsType.size(); i++) {
                NewsType newsType = listNewsType.get(i);
                mListFragments.add(NewsListFragment.newInstance(newsType));
                titles.add(newsType.getTypename());
            }
        }
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewsTypeCrudEvent event) {
        mIsfresh = true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        KLog.v("MYTAG", "onHiddenChanged start..." + hidden);
        if (mIsfresh && !hidden) {
            KLog.v("MYTAG", "ViewPager refresh start...");
            mIsfresh = false;
            mViewPager.setCurrentItem(mListFragments.size() - 1);
            initNewsListFragment();
            //mViewPager.setOffscreenPageLimit(mListFragments.size());
            mNewsFragmentAdapter.refreshViewPager(mListFragments);
        }
    }
}
