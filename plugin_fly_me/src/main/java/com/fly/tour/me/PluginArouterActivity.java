package com.fly.tour.me;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.fly.tour.common.base.BaseActivity;
import com.fly.tour.me.fragment.MainMeFragment;


/**
 * Description: <PluginArouterActivity><br>
 * Author:      mxdl<br>
 * Date:        2020/3/5<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PluginArouterActivity extends BaseActivity {
    @Override
    public int onBindLayout() {
        return R.layout.activity_plugin_arouter;
    }

    @Override
    public void initView() {
        Fragment fragment = Fragment.instantiate(this,"com.fly.tour.news.fragment.MainNewsFragment");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_find, fragment).commit();
    }

    @Override
    public void initData() {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("key","123456");
                intent.setClassName("com.fly.tour.find","com.fly.tour.find.MainFindActivity");
                startActivity(intent);
            }
        },15);
    }
}
