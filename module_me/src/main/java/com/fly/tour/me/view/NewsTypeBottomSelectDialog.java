package com.fly.tour.me.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.tour.common.base.BaseAdapter;
import com.fly.tour.common.util.DisplayUtil;
import com.fly.tour.db.dao.NewsTypeDao;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.me.R;
import com.fly.tour.me.adapter.NewsTypeSelectAdapter;
import com.fly.tour.me.model.NewsTypeListModel;

/**
 * Description: <PhotoSelectDialog><br>
 * Author: gxl<br>
 * Date: 2019/1/3<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class NewsTypeBottomSelectDialog extends BottomSheetDialogFragment {
    public static final String TAG = NewsTypeBottomSelectDialog.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private BaseAdapter.OnItemClickListener mItemClickListener;

    public void setItemClickListener(BaseAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public static NewsTypeBottomSelectDialog newInstance() {
        return new NewsTypeBottomSelectDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //getDialog().getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_select, container, false);
        mRecyclerView = view.findViewById(R.id.recview_me_news_type);
        NewsTypeSelectAdapter adapter = new NewsTypeSelectAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != 0){
                    outRect.top = 1;
                }

            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#000000"));
                c.drawLine(0,0,0,DisplayUtil.dip2px(1), paint);
            }
        });
        adapter.refresh(new NewsTypeListModel(getContext(),new NewsTypeDao(getContext())).getListNewsType());
        adapter.setItemClickListener(new BaseAdapter.OnItemClickListener<NewsType>() {
            @Override
            public void onItemClick(NewsType newsType, int position) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(newsType,position);
                }
                dismiss();
            }
        });
        return view;
    }

}
