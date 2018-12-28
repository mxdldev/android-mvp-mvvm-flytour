package com.fly.tour.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description: <公共的Fragment基类><br>
 * Author: gxl<br>
 * Date: 2018/6/6<br>
 * Version: V1.0.0<br>
 * Update: <br>
 *
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mContext;
    public View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = initView(inflater, container);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }
    public <M extends View> M findView(View view, int id) {
        return (M) view.findViewById(id);
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData(Bundle savedInstanceState);

}
