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

/**
 * Description: <NewsTypeListAdapter><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeListAdapter(context: Context) :
    BaseAdapter<NewsType, NewsTypeListAdapter.MyViewHodler>(context) {
    private var mDeleteClickListener: DeleteClickListener? = null

    interface DeleteClickListener {
        fun onClickDeleteListener(id: Int)
    }

    fun setDeleteClickLisenter(deleteClickLisenter: DeleteClickListener) {
        mDeleteClickListener = deleteClickLisenter;
    }

    override fun onBindLayout(): Int {
        return R.layout.item_news_type_list
    }

    override fun onCreateHolder(view: View): MyViewHodler {
        return MyViewHodler(view)
    }

    override fun onBindData(holder: MyViewHodler, e: NewsType, positon: Int) {
        holder.mTxtNewTypeTitle.text = e.typename
        holder.mTxtNewTypeTime.text = DateUtil.formatDate(
            e.addtime!!,
            DateUtil.FormatType.MMddHHmm
        )
    }

    inner class MyViewHodler(view: View) : RecyclerView.ViewHolder(view) {
        var mTxtNewTypeTitle: TextView = view.findViewById(R.id.txt_me_news_type_title)
        var mTxtNewTypeTime: TextView = view.findViewById(R.id.txt_me_news_type_time)
        var mBtnNewsTypeDelete: Button = view.findViewById(R.id.btn_me_news_type_delete)

        init {
            mBtnNewsTypeDelete.setOnClickListener {
                if (mDeleteClickListener != null) {
                    mDeleteClickListener!!.onClickDeleteListener(mList[layoutPosition].id)
                }
            }
        }

    }
}