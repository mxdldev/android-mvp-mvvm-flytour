package com.fly.tour.news.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fly.tour.common.base.BaseAdapter
import com.fly.tour.db.entity.NewsDetail
import com.fly.tour.news.R

/**
 * Description: <NewsListAdapter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsListAdapter(mContext: Context) :
    BaseAdapter<NewsDetail, NewsListAdapter.MyViewHodler>(mContext) {
    override fun onBindLayout(): Int {
        return R.layout.item_news_list
    }

    override fun onCreateHolder(view: View): MyViewHodler {
        return MyViewHodler(view)
    }

    override fun onBindData(holder: MyViewHodler, newsDetail: NewsDetail, positon: Int) {
        holder.txtNewsDetailIdValue.setText(newsDetail.id.toString())
        holder.txtNewsDetailTitleValue.setText(newsDetail.title)
        holder.txtNewsDetailContentValue.setText(newsDetail.content)
        holder.txtNewsDetailAddtimeValue.setText(newsDetail.addtime)
    }

    inner class MyViewHodler(view: View) : RecyclerView.ViewHolder(view) {
        var txtNewsDetailIdValue: TextView = view.findViewById(R.id.txt_news_detail_id_value)
        var txtNewsDetailTitleValue: TextView = view.findViewById(R.id.txt_news_detail_title_value)
        var txtNewsDetailContentValue: TextView =
            view.findViewById(R.id.txt_news_detail_content_value)
        var txtNewsDetailAddtimeValue: TextView =
            view.findViewById(R.id.txt_news_detail_addtime_value)
    }
}