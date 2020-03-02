package com.fly.tour.news.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.fly.tour.common.base.BaseFragment
import com.fly.tour.common.event.me.NewsTypeCrudEvent
import com.fly.tour.common.manager.NewsDBManager
import com.fly.tour.common.util.log.KLog
import com.fly.tour.news.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_news_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Description: <MainNewsFragment><br>
 * Author:      mxdl<br>
 * Date:        2020/2/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class MainNewsFragment : BaseFragment() {
    private var titles = ArrayList<String>()
    private var mListFragment = ArrayList<NewsListFragment>()
    private var mIsfresh = false;
    private lateinit var mNewsFragmentAdapter:NewsFragmentAdapter
    override fun onBindLayout(): Int {
        return R.layout.fragment_news_main
    }

    override fun initData() {
    }

    override fun initListener() {
        initNewsListFragment()
        mNewsFragmentAdapter = NewsFragmentAdapter(childFragmentManager)
        mNewsFragmentAdapter.refreshViewPager(mListFragment)
        mViewPager.adapter = mNewsFragmentAdapter

        mTabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
                mListFragment.get(p0!!.position).autoLoadData()
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }

        })
        mTabLayout.setupWithViewPager(mViewPager)
    }
    companion object{
        fun newInstance():MainNewsFragment{
            return MainNewsFragment()
        }
    }
    inner class NewsFragmentAdapter(var mFragmentManager:FragmentManager): FragmentPagerAdapter(mFragmentManager) {
        var pages: List<NewsListFragment>? = null
        override fun getItem(position: Int): Fragment {
            return pages!!.get(position)
        }

        override fun getCount(): Int {
            return pages!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles.get(position)
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
        fun refreshViewPager(listFragments:List<NewsListFragment>){
            val beginTransaction = mFragmentManager.beginTransaction()
            for(fragment in listFragments){
                beginTransaction.remove(fragment)
            }
            beginTransaction.commit()
            mFragmentManager.executePendingTransactions()
            pages = listFragments
            notifyDataSetChanged()
            mViewPager.currentItem = 0
        }
    }
    fun initNewsListFragment(){
        val listNewsType = NewsDBManager.getInstance(mActivity)?.getListNewsType()
        mListFragment.clear()
        titles.clear()
        if (listNewsType != null) {
            for(newsType in listNewsType){
                mListFragment.add(NewsListFragment.newInstance(newsType))
                titles.add(newsType.typename!!)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        KLog.v("MYTAG", "onHiddenChanged start...$hidden")
        if (mIsfresh && !hidden) {
            KLog.v("MYTAG", "ViewPager refresh start...")
            mIsfresh = false
            mViewPager.currentItem = mListFragment.size - 1
            initNewsListFragment()
            //mViewPager.setOffscreenPageLimit(mListFragments.size());
            mNewsFragmentAdapter.refreshViewPager(mListFragment)
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NewsTypeCrudEvent<Any>) {
        mIsfresh = true
    }
}