package com.fly.tour.main;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.fly.tour.find.fragment.DiscoverFragment;
import com.fly.tour.main.entity.MainChannel;
import com.fly.tour.me.fragment.MeFragment;
import com.fly.tour.trip.fragment.TripMainFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity {
    private TripMainFragment mTripMainFragment;
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
                    switchContent(mCurrFragment, mTripMainFragment, MainChannel.TRIP.name);
                    mCurrFragment = mTripMainFragment;

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

        mTripMainFragment = TripMainFragment.newInstance();
        mDiscoverFragment = DiscoverFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        mCurrFragment = mTripMainFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mTripMainFragment, MainChannel.TRIP.name).commit();
        // startActivity(new Intent(this,TestActivity.class));
//        new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//
//            }
//        });
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
