package com.fly.tour.common.binding.viewadapter.recycleview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fly.tour.common.binding.command.BindingCommand;
import com.refresh.lib.BaseRefreshLayout;
import com.refresh.lib.DaisyRefreshLayout;

/**
 * Description: <ViewAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/7/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class ViewAdapter {
    @BindingAdapter({"linearLayoutManager"})
    public static void setLinearLayoutManager(RecyclerView recyclerView,boolean b) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(b?LinearLayoutManager.HORIZONTAL:LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}
