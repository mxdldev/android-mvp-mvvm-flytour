package com.fly.tour.me;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fly.tour.common.base.BaseRefreshActivity;
import com.fly.tour.common.event.RequestCode;
import com.fly.tour.common.util.ToastUtil;
import com.fly.tour.common.view.CommonDialogFragment;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.me.adapter.NewsTypeShowAdapter;
import com.fly.tour.me.contract.NewsTypeListContract;
import com.fly.tour.me.inject.component.DaggerNewsTypeListComponent;
import com.fly.tour.me.inject.module.NewsTypeListModule;
import com.fly.tour.me.model.NewsTypeListModel;
import com.fly.tour.me.presenter.NewsTypeListPresenter;

import java.util.List;
/**
 * Description: <NewsTypeBottomSelectDialog><br>
 * Author:      mxdl<br>
 * Date:        2019/05/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListActivity extends BaseRefreshActivity<NewsTypeListModel, NewsTypeListContract.View<NewsType>, NewsTypeListPresenter, NewsType> implements NewsTypeListContract.View<NewsType> {

    private RecyclerView mRecViewNewsType;
    private NewsTypeShowAdapter mNewsTypeShowAdapter;

    public int onBindLayout() {
        return R.layout.activity_news_type_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_add) {
            startActivityForResult(new Intent(this, NewsTypeAddActivity.class), RequestCode.Me.NEWS_TYPE_ADD);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        mRecViewNewsType = findViewById(R.id.recview);
        mRecViewNewsType.setLayoutManager(new LinearLayoutManager(this));
        mNewsTypeShowAdapter = new NewsTypeShowAdapter(this);
        mRecViewNewsType.setAdapter(mNewsTypeShowAdapter);
    }

    @Override
    public void initListener() {

        mNewsTypeShowAdapter.setDeleteClickLisenter(new NewsTypeShowAdapter.DeleteClickLisenter() {
            @Override
            public void onClickDeleteListener(final int id) {
                new CommonDialogFragment.Builder().setTitle("信息提示").setDescribe("确定删除吗？").setLeftbtn("取消").setRightbtn("确定").setOnDialogClickListener(new CommonDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onLeftBtnClick(View view) {

                    }

                    @Override
                    public void onRightBtnClick(View view) {
                        mPresenter.deleteNewsTypeById(id);
                    }
                }).build().show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.refreshData();
    }

    @Override
    public void injectPresenter() {
        DaggerNewsTypeListComponent.builder().newsTypeListModule(new NewsTypeListModule(this)).build().inject(this);
    }

    @Override
    protected int onBindRreshLayout() {
        return R.id.refview_news_type;
    }


    @Override
    public void onRefreshEvent() {
        mPresenter.refreshData();
    }

    @Override
    public void onLoadMoreEvent() {
        ToastUtil.showToast("没有更多数据了");
        stopLoadMore();
    }

    @Override
    public void onAutoLoadEvent() {

    }

    @Override
    public void refreshData(List<NewsType> data) {
        mNewsTypeShowAdapter.refresh(data);
    }

    @Override
    public void loadMoreData(List<NewsType> data) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RequestCode.Me.NEWS_TYPE_ADD:
                if(resultCode == Activity.RESULT_OK){
                    mPresenter.refreshData();
                }
                break;
        }
    }
}
