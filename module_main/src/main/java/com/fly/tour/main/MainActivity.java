package com.fly.tour.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.fly.tour.find.fragment.DiscoverFragment;
import com.fly.tour.main.entity.MainChannel;
import com.fly.tour.me.fragment.MeFragment;
import com.fly.tour.news.fragment.MainNewsFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


public class MainActivity extends RxAppCompatActivity {
    private MainNewsFragment mMainNewsFragment;
    private DiscoverFragment mDiscoverFragment;
    private Fragment mCurrFragment;//当前的Fragment
    private MeFragment mMeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int i = menuItem.getItemId();
                if (i == R.id.navigation_trip) {
                    switchContent(mCurrFragment, mMainNewsFragment, MainChannel.TRIP.name);
                    mCurrFragment = mMainNewsFragment;

                    return true;
                } else if (i == R.id.navigation_discover) {
                    switchContent(mCurrFragment, mDiscoverFragment, MainChannel.DISCOVER.name);
                    mCurrFragment = mDiscoverFragment;

                    return true;
                } else if (i == R.id.navigation_me) {
                    switchContent(mCurrFragment, mMeFragment, MainChannel.ME.name);
                    mCurrFragment = mMeFragment;

                    return true;
                }
                return false;
            }
        });

        mMainNewsFragment = MainNewsFragment.newInstance();
        mDiscoverFragment = DiscoverFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        mCurrFragment = mMainNewsFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mMainNewsFragment, MainChannel.TRIP.name).commit();
    }
    public void switchContent(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.frame_content, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }

}
