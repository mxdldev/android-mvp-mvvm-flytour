package com.fly.tour.me

import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import com.fly.tour.common.base.BaseActivity

/**
 * Description: <PluginArouterActivity><br></br>
 * Author:      mxdl<br></br>
 * Date:        2020/3/5<br></br>
 * Version:     V1.0.0<br></br>
 * Update:     <br></br>
</PluginArouterActivity> */
class PluginArouterActivity : BaseActivity() {
    override fun onBindLayout(): Int {
        return R.layout.activity_plugin_arouter
    }

    fun initView() {
        val fragment = Fragment.instantiate(this, "com.fly.tour.news.fragment.MainNewsFragment")
        supportFragmentManager.beginTransaction().replace(R.id.frame_content_find, fragment).commit()
    }

    override fun initData() {
        Handler(mainLooper).postDelayed({
            val intent = Intent()
            intent.putExtra("key", "123456")
            intent.setClassName("com.fly.tour.find", "com.fly.tour.find.MainFindActivity")
            startActivity(intent)
        }, 15)
    }
}