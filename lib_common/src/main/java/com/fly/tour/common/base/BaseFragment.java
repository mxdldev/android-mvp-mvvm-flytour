package com.fly.tour.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.fly.tour.common.R;
import com.fly.tour.common.event.common.BaseFragmentEvent;
import com.fly.tour.common.mvp.BaseView;
import com.fly.tour.common.util.NetUtil;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.common.view.LoadingInitView;
import com.fly.tour.common.view.LoadingTransView;
import com.fly.tour.common.view.NetErrorView;
import com.fly.tour.common.view.NoDataView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Description: <BaseFragment><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected RxAppCompatActivity mActivity;
    protected View mView;
    protected TextView mTxtTitle;
    protected Toolbar mToolbar;

    protected NetErrorView mNetErrorView;
    protected NoDataView mNoDataView;
    protected LoadingInitView mLoadingInitView;
    protected LoadingTransView mLoadingTransView;

    private ViewStub mViewStubToolbar;
    private ViewStub mViewStubContent;
    private ViewStub mViewStubInitLoading;
    private ViewStub mViewStubTransLoading;
    private ViewStub mViewStubNoData;
    private ViewStub mViewStubError;
    private boolean isViewCreated = false;
    private boolean isViewVisable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RxAppCompatActivity) getActivity();
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_root, container, false);
        initCommonView(mView);
        initView(mView);
        initListener();
        return mView;
    }

    public void initCommonView(View view) {
        mViewStubToolbar = view.findViewById(R.id.view_stub_toolbar);
        mViewStubContent = view.findViewById(R.id.view_stub_content);
        mViewStubContent = view.findViewById(R.id.view_stub_content);
        mViewStubInitLoading = view.findViewById(R.id.view_stub_init_loading);
        mViewStubTransLoading = view.findViewById(R.id.view_stub_trans_loading);
        mViewStubNoData = view.findViewById(R.id.view_stub_nodata);
        mViewStubError = view.findViewById(R.id.view_stub_error);

        if (enableToolbar()) {
            mViewStubToolbar.setLayoutResource(onBindToolbarLayout());
            View viewTooBbar = mViewStubToolbar.inflate();
            initTooBar(viewTooBbar);
        }
        mViewStubContent.setLayoutResource(onBindLayout());
        mViewStubContent.inflate();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        //如果启用了懒加载就进行懒加载，否则就进行预加载
        if (enableLazyData()) {
            lazyLoad();
        } else {
            initData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisable = isVisibleToUser;
        //如果启用了懒加载就进行懒加载，
        if (enableLazyData() && isViewVisable) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,必须确保onCreateView加载完毕且页面可见,才加载数据
        KLog.v("MYTAG","lazyLoad start...");
        KLog.v("MYTAG","isViewCreated:"+isViewCreated);
        KLog.v("MYTAG","isViewVisable"+isViewVisable);
        if (isViewCreated && isViewVisable) {
            initData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isViewVisable = false;
        }
    }
    //默认不启用懒加载
    public boolean enableLazyData() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void initTooBar(View view) {
        mToolbar = view.findViewById(R.id.toolbar_root);
        mTxtTitle = view.findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);
            mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
        }
        if (mTxtTitle != null) {
            mTxtTitle.setText(getToolbarTitle());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEvent(BaseFragmentEvent<T> event) {
    }

    public abstract int onBindLayout();

    public abstract void initView(View view);

    public void initView() {
    }

    public abstract void initData();

    public abstract String getToolbarTitle();

    public void initListener() {
    }

    @Override
    public void finishActivity() {
        mActivity.finish();
    }

    public boolean enableToolbar() {
        return false;
    }

    public int onBindToolbarLayout() {
        return R.layout.common_toolbar;
    }

    public void showInitLoadView() {
        showInitLoadView(true);
    }

    public void hideInitLoadView() {
        showInitLoadView(false);
    }

    @Override
    public void showTransLoadingView() {
        showTransLoadingView(true);
    }

    @Override
    public void hideTransLoadingView() {
        showTransLoadingView(false);
    }

    public void showNoDataView() {
        showNoDataView(true);
    }
    public void showNoDataView(int resid) {
        showNoDataView(true,resid);
    }
    public void hideNoDataView() {
        showNoDataView(false);
    }

    public void showNetWorkErrView() {
        showNetWorkErrView(true);
    }

    public void hideNetWorkErrView() {
        showNetWorkErrView(false);
    }

    private void showInitLoadView(boolean show) {
        if (mLoadingInitView == null) {
            View view = mViewStubInitLoading.inflate();
            mLoadingInitView = view.findViewById(R.id.view_init_loading);
        }
        mLoadingInitView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingInitView.loading(show);
    }


    private void showNetWorkErrView(boolean show) {
        if (mNetErrorView == null) {
            View view = mViewStubError.inflate();
            mNetErrorView = view.findViewById(R.id.view_net_error);
            mNetErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetUtil.checkNetToast()) {
                        return;
                    }
                    hideNetWorkErrView();
                    initData();
                }
            });
        }
        mNetErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void showNoDataView(boolean show) {
        if (mNoDataView == null) {
            View view = mViewStubNoData.inflate();
            mNoDataView = view.findViewById(R.id.view_no_data);
        }
        mNoDataView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    private void showNoDataView(boolean show,int resid) {
        showNoDataView(show);
        if(show){
            mNoDataView.setNoDataView(resid);
        }
    }
    private void showTransLoadingView(boolean show) {
        if (mLoadingTransView == null) {
            View view = mViewStubTransLoading.inflate();
            mLoadingTransView = view.findViewById(R.id.view_trans_loading);
        }
        mLoadingTransView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingTransView.loading(show);
    }

}
