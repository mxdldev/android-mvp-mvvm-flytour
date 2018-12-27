package com.fly.tour.trip.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fly.tour.common.base.BaseFragment;
import com.fly.tour.trip.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Description: <TripMainFragment><br>
 * Author:      gxl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TripMainFragment extends BaseFragment {
    private String[] titles = {"线路","记录","组队"};
    private List<Fragment> mArrayList = new ArrayList<Fragment>(){
        {
            add(TripRouteFragment.newInstance());
            add(TripRecordFragment.newInstance());
            add(TripTeamFragment.newInstance());
        }
    } ;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    Toast.makeText(getContext(), "没有相关权限", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static TripMainFragment newInstance(){
        return new TripMainFragment();
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_trip_main, container,false);
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
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
