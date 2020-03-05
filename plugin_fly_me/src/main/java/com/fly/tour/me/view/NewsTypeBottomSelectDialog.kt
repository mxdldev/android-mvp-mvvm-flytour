package com.fly.tour.me.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fly.tour.common.base.BaseAdapter.OnItemClickListener
import com.fly.tour.common.util.DisplayUtil
import com.fly.tour.db.entity.NewsType
import com.fly.tour.me.R
import com.fly.tour.me.adapter.NewsTypeSelectAdapter
import com.fly.tour.me.model.NewsTypeListModel

/**
 * Description: <PhotoSelectDialog><br>
 * Author: mxdl<br>
 * Date: 2019/1/3<br>
 * Version: V1.0.0<br>
 * Update: <br>
</PhotoSelectDialog> */
class NewsTypeBottomSelectDialog : BottomSheetDialogFragment() {
    private var mRecyclerView: RecyclerView? = null
    private var mItemClickListener: OnItemClickListener<NewsType>? = null

    fun setItemClickListener(itemClickListener: OnItemClickListener<NewsType>) {
        mItemClickListener = itemClickListener
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        //getDialog().getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_select, container, false)
        mRecyclerView = view.findViewById(R.id.recview_me_news_type)
        val adapter = NewsTypeSelectAdapter(context!!)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.addItemDecoration(object :
            RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top = 1
                }

            }

            override fun onDraw(
                c: Canvas,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.onDraw(c, parent, state)
                val paint = Paint()
                paint.color = Color.parseColor("#000000")
                c.drawLine(0f, 0f, 0f, DisplayUtil.dip2px(1f).toFloat(), paint)
            }
        })
        adapter.refresh(NewsTypeListModel(context!!).getListNewsType())
        adapter.setItemClickListener(object : OnItemClickListener<NewsType> {
            override fun onItemClick(e: NewsType, position: Int) {
                mItemClickListener?.onItemClick(e, position as Int)
                dismiss()
            }
        })
        return view
    }

    companion object {
        val TAG = NewsTypeBottomSelectDialog::class.java!!.getSimpleName()

        fun newInstance(): NewsTypeBottomSelectDialog {
            return NewsTypeBottomSelectDialog()
        }
    }

}
