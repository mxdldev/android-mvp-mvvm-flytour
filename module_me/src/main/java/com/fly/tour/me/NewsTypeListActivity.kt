package com.fly.tour.me

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fly.tour.common.base.BaseRefreshActivity
import com.fly.tour.common.event.RequestCode
import com.fly.tour.common.view.CommonDialogFragment
import com.fly.tour.db.entity.NewsType
import com.fly.tour.me.adapter.NewsTypeListAdapter
import com.fly.tour.me.adapter.NewsTypeListAdapter.DeleteClickListener
import com.fly.tour.me.contract.NewsTypeListContract
import com.fly.tour.me.model.NewsTypeListModel
import com.fly.tour.me.presenter.NewsTypeListPresenter
import kotlinx.android.synthetic.main.activity_news_type_list.*
/**
 * Description: <NewsTypeListActivity><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeListActivity :
    BaseRefreshActivity<NewsTypeListModel, NewsTypeListContract.View<NewsType>, NewsTypeListPresenter, NewsType>(),
    NewsTypeListContract.View<NewsType> {
    private var mNewsTypeListAdapter: NewsTypeListAdapter? = null
    override fun onBindRreshLayout(): Int {
        return R.id.mRefViewNewsType
    }

    override fun initPresenter(): NewsTypeListPresenter {
        return NewsTypeListPresenter(this)
    }

    override fun onBindLayout(): Int {
        return R.layout.activity_news_type_list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var i = item.itemId
        if (i == R.id.menu_add) {
            startActivityForResult(
                Intent(this, NewsTypeAddActivity::class.java),
                RequestCode.Me.NEWS_TYPE_ADD
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initListener() {
        mRecViewNewsType.setLayoutManager(LinearLayoutManager(this))
        mNewsTypeListAdapter = NewsTypeListAdapter(this)
        mRecViewNewsType.setAdapter(mNewsTypeListAdapter)
        mNewsTypeListAdapter?.setDeleteClickLisenter(object : DeleteClickListener {
            override fun onClickDeleteListener(id: Int) {
                CommonDialogFragment.Builder().setTitle("信息提示").setDescribe("确定删除吗？")
                    .setLeftbtn("取消").setRightbtn("确定")
                    .setOnDialogClickListener(object : CommonDialogFragment.OnDialogClickListener {
                        override fun onLeftBtnClick(view: View) {

                        }

                        override fun onRightBtnClick(view: View) {
                            mPresenter?.deleteNewsTypeById(id)
                        }
                    }).build().show(supportFragmentManager, "dialog")
            }
        })
    }

    override fun initData() {
        mPresenter?.refreshData()
    }

    override fun refreshData(data: List<NewsType>) {
        mNewsTypeListAdapter?.refresh(data)
    }


    override fun loadMoreData(data: List<NewsType>) {
    }


    override fun onRefreshEvent() {
        mPresenter?.refreshData()
    }

    override fun onLoadMoreEvent() {
        mPresenter?.loadMoreData()
    }

    override fun onAutoLoadEvent() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.Me.NEWS_TYPE_ADD -> mPresenter?.refreshData()
        }
    }

    companion object {
        fun startNewsTypeListActivity(context: Context) {
            context.startActivity(Intent(context, NewsTypeListActivity::class.java))
        }
    }
}
