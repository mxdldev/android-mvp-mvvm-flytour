package com.fly.tour.me.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.adapter.BaseBindingAdapter;
import com.fly.tour.me.R;
import com.fly.tour.me.databinding.ItemNewsTypeShowBindingBinding;

/**
 * Description: <NewsTypeShowBindingAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/7/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeShowBindingAdapter extends BaseBindingAdapter<NewsType, ItemNewsTypeShowBindingBinding> {
    private NewsTypeShowBindingAdapter.DeleteClickLisenter mDeleteClickLisenter;

    public interface DeleteClickLisenter {
        void onClickDeleteListener(int id);
    }

    public void setDeleteClickLisenter(NewsTypeShowBindingAdapter.DeleteClickLisenter deleteClickLisenter) {
        mDeleteClickLisenter = deleteClickLisenter;
    }
    public NewsTypeShowBindingAdapter(Context context, ObservableArrayList<NewsType> items) {
        super(context, items);
    }
    @Override
    protected int getLayoutItemId(int viewType) {
        return R.layout.item_news_type_show_binding;
    }

    @Override
    protected void onBindItem(ItemNewsTypeShowBindingBinding binding, final NewsType item, int position) {
        binding.setNewsType(item);
        binding.btnMeNewsTypeDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mDeleteClickLisenter != null){
                    mDeleteClickLisenter.onClickDeleteListener(item.getId());
                }
            }
        });
        binding.layoutNewstypeList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
    }
}
