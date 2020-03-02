package com.fly.tour.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.view.ViewStub
import android.widget.TextView

import com.alibaba.android.arouter.launcher.ARouter
import com.fly.tour.common.R
import com.fly.tour.common.event.common.BaseActivityEvent
import com.fly.tour.common.manager.ActivityManager
import com.fly.tour.common.mvp.BaseView
import com.fly.tour.common.util.NetUtil
import com.fly.tour.common.view.LoadingInitView
import com.fly.tour.common.view.LoadingTransView
import com.fly.tour.common.view.NetErrorView
import com.fly.tour.common.view.NoDataView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
/**
 * Description: <BaseActivity><br>
 * Author:      mxdl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseActivity> */
abstract class BaseActivity : RxAppCompatActivity(), BaseView {
    protected lateinit var mTxtTitle: TextView
    protected lateinit var mToolbar: Toolbar

    protected var mNetErrorView: NetErrorView? = null
    protected var mNoDataView: NoDataView? = null
    protected var mLoadingInitView: LoadingInitView? = null
    protected var mLoadingTransView: LoadingTransView? = null

    private lateinit var mViewStubToolbar: ViewStub
    private lateinit var mViewStubContent: ViewStub
    private lateinit var mViewStubInitLoading: ViewStub
    private lateinit var mViewStubTransLoading: ViewStub
    private lateinit var mViewStubNoData: ViewStub
    private lateinit var mViewStubError: ViewStub
    private val isrefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_root)
        initCommonView()
        ARouter.getInstance().inject(this)
        initListener()
        initData()
        EventBus.getDefault().register(this)
        ActivityManager.getInstance()?.addActivity(this)
    }


    protected open fun initCommonView() {
        mViewStubToolbar = findViewById(R.id.view_stub_toolbar)
        mViewStubContent = findViewById(R.id.view_stub_content)
        mViewStubContent = findViewById(R.id.view_stub_content)
        mViewStubInitLoading = findViewById(R.id.view_stub_init_loading)
        mViewStubTransLoading = findViewById(R.id.view_stub_trans_loading)
        mViewStubError = findViewById(R.id.view_stub_error)
        mViewStubNoData = findViewById(R.id.view_stub_nodata)

        if (enableToolbar()) {
            mViewStubToolbar.layoutResource = onBindToolbarLayout()
            val view = mViewStubToolbar.inflate()
            initToolbar(view)
        }
        mViewStubContent.layoutResource = onBindLayout()
        mViewStubContent.inflate()
    }

    protected fun initToolbar(view: View) {
        mToolbar = view.findViewById(R.id.toolbar_root)
        mTxtTitle = view.findViewById(R.id.toolbar_title)
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            mToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        if (mTxtTitle != null && !TextUtils.isEmpty(title)) {
            mTxtTitle.text = title
        }
        //可以再次覆盖设置title
        val tootBarTitle = getTootBarTitle()
        if (mTxtTitle != null && !TextUtils.isEmpty(tootBarTitle)) {
            mTxtTitle.text = tootBarTitle
        }
    }
    open fun getTootBarTitle(): String{
        return ""
    }

    open fun onBindToolbarLayout(): Int {
        return R.layout.common_toolbar
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        ActivityManager.getInstance()?.finishActivity(this)
    }

    abstract fun onBindLayout(): Int

    abstract override fun initData()

    override fun initListener() {}

    override fun finishActivity() {
        finish()
    }

    fun enableToolbar(): Boolean {
        return true
    }

    override fun showInitLoadView() {
        showInitLoadView(true)
    }

    override fun hideInitLoadView() {
        showInitLoadView(false)
    }

    override fun showTransLoadingView() {
        showTransLoadingView(true)
    }

    override fun hideTransLoadingView() {
        showTransLoadingView(false)
    }

    override fun showNoDataView() {
        showNoDataView(true)
    }

    override fun showNoDataView(resid: Int) {
        showNoDataView(true, resid)
    }

    override fun hideNoDataView() {
        showNoDataView(false)
    }

    override fun hideNetWorkErrView() {
        showNetWorkErrView(false)
    }

    override fun showNetWorkErrView() {
        showNetWorkErrView(true)
    }

    private fun showInitLoadView(show: Boolean) {
        if (mLoadingInitView == null) {
            val view = mViewStubInitLoading.inflate()
            mLoadingInitView = view.findViewById(R.id.view_init_loading)
        }
        mLoadingInitView?.visibility = if (show) View.VISIBLE else View.GONE
        mLoadingInitView?.loading(show)
    }


    private fun showNetWorkErrView(show: Boolean) {
        if (mNetErrorView == null) {
            val view = mViewStubError.inflate()
            mNetErrorView = view.findViewById(R.id.view_net_error)
            mNetErrorView?.setOnClickListener(View.OnClickListener {
                if (!NetUtil.checkNetToast()) {
                    return@OnClickListener
                }
                hideNetWorkErrView()
                initData()
            })
        }
        mNetErrorView?.visibility = if (show) View.VISIBLE else View.GONE
    }


    private fun showNoDataView(show: Boolean) {
        if (mNoDataView == null) {
            val view = mViewStubNoData.inflate()
            mNoDataView = view.findViewById(R.id.view_no_data)
        }
        mNoDataView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showNoDataView(show: Boolean, resid: Int) {
        showNoDataView(show)
        if (show) {
            mNoDataView?.setNoDataView(resid)
        }
    }

    private fun showTransLoadingView(show: Boolean) {
        if (mLoadingTransView == null) {
            val view = mViewStubTransLoading.inflate()
            mLoadingTransView = view.findViewById(R.id.view_trans_loading)
        }
        mLoadingTransView?.visibility = if (show) View.VISIBLE else View.GONE
        mLoadingTransView?.loading(show)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: BaseActivityEvent<T>) {
    }

    companion object {
        protected val TAG = BaseActivity::class.java.getSimpleName()
    }
}
