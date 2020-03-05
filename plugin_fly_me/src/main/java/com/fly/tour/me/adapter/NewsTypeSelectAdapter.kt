package com.fly.tour.me.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.fly.tour.common.base.BaseAdapter
import com.fly.tour.common.util.DateUtil
import com.fly.tour.db.entity.NewsType
import com.fly.tour.me.R
import com.fly.tour.me.adapter.NewsTypeSelectAdapter.*

/**
 * Description: <NewsTypeShowAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</NewsTypeShowAdapter> */
class NewsTypeSelectAdapter(context: Context) : BaseAdapter<NewsType, MyViewHolder>(context) {

    override fun onBindLayout(): Int {
        return R.layout.item_news_type_select
    }

    override fun onCreateHolder(view: View): MyViewHolder {
        return MyViewHolder(view)
    }

    override fun onBindData(holder: MyViewHolder, newsType: NewsType, positon: Int) {
        holder.mTxtNewTypeTitle.text = newsType.typename
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTxtNewTypeTitle: TextView

        init {
            mTxtNewTypeTitle = itemView.findViewById(R.id.txt_me_news_type_title)

        }
    }
}
