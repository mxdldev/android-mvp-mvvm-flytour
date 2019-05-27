package com.fly.tour.me.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fly.tour.common.base.BaseAdapter;
import com.fly.tour.common.util.DateUtil;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.me.R;

/**
 * Description: <NewsTypeListAdapter><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListAdapter extends BaseAdapter<NewsType, NewsTypeListAdapter.MyViewHolder> {
    private DeleteClickLisenter mDeleteClickLisenter;

    public interface DeleteClickLisenter {
        void onClickDeleteListener(int id);
    }

    public void setDeleteClickLisenter(DeleteClickLisenter deleteClickLisenter) {
        mDeleteClickLisenter = deleteClickLisenter;
    }

    public NewsTypeListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_news_type;
    }

    @Override
    protected MyViewHolder onCreateHolder(View view) {
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindData(MyViewHolder holder, NewsType newsType, int positon) {
        holder.mTxtNewTypeTitle.setText(newsType.getTypename());
        holder.mTxtNewTypeTime.setText(DateUtil.formatDate(newsType.getAddtime(),DateUtil.FormatType.MMddHHmm));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtNewTypeTitle;
        TextView mTxtNewTypeTime;
        Button mBtnNewsTypeDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtNewTypeTitle = itemView.findViewById(R.id.txt_me_news_type_title);
            mTxtNewTypeTime = itemView.findViewById(R.id.txt_me_news_type_time);
            mBtnNewsTypeDelete = itemView.findViewById(R.id.btn_me_news_type_delete);
            mBtnNewsTypeDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickLisenter != null) {
                        mDeleteClickLisenter.onClickDeleteListener(mList.get(getLayoutPosition()).getId());
                    }
                }
            });
        }
    }
}
