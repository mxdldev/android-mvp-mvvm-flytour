package com.fly.tour.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gxl on 2017/12/25.
 */

public abstract class BaseHeaderRecycleAdapter<E, VH extends RecyclerView.ViewHolder> extends BaseRecycelerViewAdapter<E, RecyclerView.ViewHolder> {
	public static final int TYPE_HEADER = 0;
	public static final int TYPE_NORMAL = 1;
	private View mHeaderView;

	public BaseHeaderRecycleAdapter(Context context) {
		super(context);
	}

	@Override
	public int getItemCount() {
		return mHeaderView == null ? mList.size() : mList.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (mHeaderView == null) {
			return TYPE_NORMAL;
		}
		if (position == 0) {
			return TYPE_HEADER;
		}
		return TYPE_NORMAL;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (mHeaderView != null && viewType == TYPE_HEADER) {
			return new HeaderViewHolder(mHeaderView) {
			};
		}
		return super.onCreateViewHolder(parent, viewType);
	};

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == TYPE_HEADER) {
			return;
		}
		super.onBindViewHolder(holder, getRealPosition(holder));
	}

	public int getRealPosition(RecyclerView.ViewHolder holder) {
		int position = holder.getLayoutPosition();
		return mHeaderView == null ? position : position - 1;
	}

	public View getHeaderView() {
		return mHeaderView;
	}

	public void setHeaderView(View headerView) {
		mHeaderView = headerView;
	}
	class HeaderViewHolder extends RecyclerView.ViewHolder{

		public HeaderViewHolder(View itemView) {
			super(itemView);
		}
	}
}
