package com.fly.tour

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import com.didi.virtualapk.PluginManager
import com.didi.virtualapk.internal.utils.PluginUtil
import com.fly.tour.common.base.BaseActivity
import com.fly.tour.entity.MainChannel


class MainActivity : BaseActivity() {
    private var mNewsFragment: Fragment? = null
    private var mFindFragment: Fragment? = null
    private var mMeFragment: Fragment? = null
    private var mCurrFragment: Fragment? = null
    override fun onBindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val i = menuItem.itemId
            if (i == R.id.navigation_trip) {
                switchContent(mCurrFragment, mNewsFragment, MainChannel.NEWS.name)
                mCurrFragment = mNewsFragment
                return@OnNavigationItemSelectedListener true
            } else if (i == R.id.navigation_discover) {
                switchContent(mCurrFragment, mFindFragment, MainChannel.FIND.name)
                mCurrFragment = mFindFragment
                return@OnNavigationItemSelectedListener true
            } else if (i == R.id.navigation_me) {
                switchContent(mCurrFragment, mMeFragment, MainChannel.ME.name)
                mCurrFragment = mMeFragment
                return@OnNavigationItemSelectedListener true
            }
            false
        })
        //PluginUtil.hookActivityResources(this, "com.fly.tour.news")
        //PluginUtil.hookActivityResources(this, "com.fly.tour.find")
        //PluginUtil.hookActivityResources(this, "com.fly.tour.me")
        if (PluginManager.getInstance(this).getLoadedPlugin("com.fly.tour.news") == null) {
            Toast.makeText(this, "plugin [com.fly.tour.news] not loaded", Toast.LENGTH_SHORT).show()
            return
        }
        mNewsFragment = Fragment.instantiate(this, "com.fly.tour.news.fragment.MainNewsFragment")
        if (PluginManager.getInstance(this).getLoadedPlugin("com.fly.tour.find") == null) {
            Toast.makeText(this, "plugin [com.fly.tour.find] not loaded", Toast.LENGTH_SHORT).show()
            return
        }
        mFindFragment = Fragment.instantiate(this, "com.fly.tour.find.fragment.MainFindFragment")
        if (PluginManager.getInstance(this).getLoadedPlugin("com.fly.tour.me") == null) {
            Toast.makeText(this, "plugin [com.fly.tour.me] not loaded", Toast.LENGTH_SHORT).show()
            return
        }
        mMeFragment = Fragment.instantiate(this, "com.fly.tour.me.fragment.MainMeFragment")
        mCurrFragment = mNewsFragment
        if (mNewsFragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_content, mNewsFragment!!, MainChannel.NEWS.name).commit()
        }
    }
    fun switchContent(from: Fragment?, to: Fragment?, tag: String?) {
        if (from == null || to == null) {
            return
        }
        val transaction = supportFragmentManager.beginTransaction()
        if (!to.isAdded) {
            transaction.hide(from).add(R.id.frame_content, to, tag).commit()
        } else {
            transaction.hide(from).show(to).commit()
        }
    }


}