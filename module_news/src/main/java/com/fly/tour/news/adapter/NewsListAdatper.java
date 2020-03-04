package com.fly.tour.news.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.view.View;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.common.adapter.BaseBindAdapter;
import com.fly.tour.news.R;
import com.fly.tour.news.databinding.ItemNewsListBinding;

/**
 * Description: <NewsListAdatper><br>
 * Author:      mxdl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListAdatper extends BaseBindAdapter<NewsDetail, ItemNewsListBinding> {


    public NewsListAdatper(Context context, ObservableArrayList<NewsDetail> items) {
        super(context, items);
    }

    @Override
    protected int getLayoutItemId(int viewType) {
        return R.layout.item_news_list;
    }

    @Override
    protected void onBindItem(ItemNewsListBinding binding, final NewsDetail item, final int position) {
        binding.setNewsDetail(item);
        binding.viewNewsDetal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(item,position);
                }
            }
        });
    }


}
