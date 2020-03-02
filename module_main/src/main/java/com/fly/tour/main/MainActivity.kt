package com.fly.tour.main

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.fly.tour.common.base.BaseActivity
import com.fly.tour.common.provider.IFindProvider
import com.fly.tour.common.provider.IMeProvider
import com.fly.tour.common.provider.INewsProvider
import com.fly.tour.common.util.ToastUtil
import com.fly.tour.main.entity.MainChannel
import kotlinx.android.synthetic.main.activity_main_index.*

class MainActivity : BaseActivity() {
    @Autowired(name = "/news/main")
    @JvmField var mNewsProvider: INewsProvider? = null

    @Autowired(name = "/find/main")
    @JvmField var mFindProvider: IFindProvider? = null

    @Autowired(name = "/me/main")
    @JvmField var mMeProvider: IMeProvider? = null

    private var mNewsFragment: Fragment? = null
    private var mFindFragment: Fragment? = null
    private var mMeFragment: Fragment? = null
    private var mCurrFragment: Fragment? = null

    override fun onBindLayout(): Int {
        return R.layout.activity_main_index
    }

    override fun initListener() {
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_news -> {
                    switchContent(mCurrFragment, mNewsFragment, MainChannel.NEWS.name)
                    mCurrFragment = mNewsFragment
                }
                R.id.navigation_discover -> {
                    switchContent(mCurrFragment, mFindFragment, MainChannel.FIND.name)
                    mCurrFragment = mFindFragment
                }
                R.id.navigation_me -> {
                    switchContent(mCurrFragment, mMeFragment, MainChannel.ME.name)
                    mCurrFragment = mMeFragment
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }
    override fun initData() {
        mNewsFragment = mNewsProvider?.mainNewsFragment
        mFindFragment = mFindProvider?.mainFindFragment
        mMeFragment = mMeProvider?.mainMeFragment

        mCurrFragment = mNewsFragment
        if (mNewsFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_content, mNewsFragment as Fragment, MainChannel.NEWS.name)
                .commit()
        }
    }

    fun switchContent(from: Fragment?, to: Fragment?, tag: String) {
        if (from == null || to == null) {
            return
        }
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (!to.isAdded) {
            beginTransaction.hide(from).add(R.id.frame_content, to, tag).commit()
        } else {
            beginTransaction.hide(from).show(to).commit()
        }
    }
}
