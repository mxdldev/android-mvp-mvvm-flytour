package com.fly.tour.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fly.tour.common.base.BaseAdapter;
import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.trip.R;

/**
 * Description: <NewsListAdatper><br>
 * Author:      gxl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListAdatper extends BaseAdapter<NewsDetail, NewsListAdatper.MyViewHolder> {
    public NewsListAdatper(Context context) {
        super(context);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_news_list;
    }

    @Override
    protected MyViewHolder onCreateHolder(View view) {
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindData(MyViewHolder holder, NewsDetail newsDetail, int positon) {
        holder.txtNewsDetailIdValue.setText(newsDetail.getId() + "");
        holder.txtNewsDetailTitleValue.setText(newsDetail.getTitle());
        holder.txtNewsDetailContentValue.setText(newsDetail.getContent());
        holder.txtNewsDetailAddtimeValue.setText(newsDetail.getAddtime());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNewsDetailIdValue;
        TextView txtNewsDetailTitleValue;
        TextView txtNewsDetailContentValue;
        TextView txtNewsDetailAddtimeValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNewsDetailIdValue = itemView.findViewById(R.id.txt_news_detail_id_value);
            txtNewsDetailTitleValue = itemView.findViewById(R.id.txt_news_detail_title_value);
            txtNewsDetailContentValue = itemView.findViewById(R.id.txt_news_detail_content_value);
            txtNewsDetailAddtimeValue = itemView.findViewById(R.id.txt_news_detail_addtime_value);
        }
    }
}
