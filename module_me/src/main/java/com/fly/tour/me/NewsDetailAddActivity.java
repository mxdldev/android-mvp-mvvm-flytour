package com.fly.tour.me;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.adapter.BaseAdapter;
import com.fly.tour.common.mvvm.BaseMvvmActivity;
import com.fly.tour.common.mvvm.BaseMvvmActivity1;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.SettingBarView;
import com.fly.tour.me.databinding.ActivityNewsDetailAddBinding;
import com.fly.tour.me.mvvm.factory.MeViewModelFactory;
import com.fly.tour.me.mvvm.viewmodel.NewsDetailAddViewModel;
import com.fly.tour.me.view.NewsTypeBottomSelectDialog;

import java.util.List;
/**
 * Description: <NewsDetailAddActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/07/02<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddActivity extends BaseMvvmActivity1<ActivityNewsDetailAddBinding,NewsDetailAddViewModel> {

    @Override
    public int onBindLayout() {
        return R.layout.activity_news_detail_add;
    }

    @Override
    public void initData() {
    }

    public void showNewsType(List<NewsType> typeList) {
        NewsTypeBottomSelectDialog newsTypeBottomSelectDialog = NewsTypeBottomSelectDialog.newInstance(typeList);
        newsTypeBottomSelectDialog.setItemClickListener(new BaseAdapter.OnItemClickListener<NewsType>() {
            @Override
            public void onItemClick(NewsType newsType, int position) {
                mViewModel.setNewsType(newsType);
                mBinding.viewMeSetNewsType.setContent(newsType.getTypename());
            }
        });
        newsTypeBottomSelectDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public Class<NewsDetailAddViewModel> onBindViewModel() {
        return NewsDetailAddViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return MeViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {
        mViewModel.getSingleNewsTypeLiveEvent().observe(this, new Observer<List<NewsType>>() {
            @Override
            public void onChanged(@Nullable List<NewsType> newsTypes) {
                showNewsType(newsTypes);
            }
        });
    }

    @Override
    public int onBindVariableId() {
        return BR.viewModel;
    }
}
