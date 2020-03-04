package com.fly.tour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.fly.tour.common.base.BaseActivity;
import com.fly.tour.entity.MainChannel;

import java.io.File;

public class MainActivity extends BaseActivity {

    private Fragment mNewsFragment;
    private Fragment mFindFragment;
    private Fragment mMeFragment;
    private Fragment mCurrFragment;

    @Override
    public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        loadPlugin(this,"plugin_fly_news-release.apk");
        loadPlugin(this,"plugin_fly_find-release.apk");
        loadPlugin(this,"plugin_fly_me-release.apk");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int i = menuItem.getItemId();
                if (i == R.id.navigation_trip) {
                    switchContent(mCurrFragment, mNewsFragment, MainChannel.NEWS.name);
                    mCurrFragment = mNewsFragment;

                    return true;
                } else if (i == R.id.navigation_discover) {
                    switchContent(mCurrFragment, mFindFragment, MainChannel.FIND.name);
                    mCurrFragment = mFindFragment;

                    return true;
                } else if (i == R.id.navigation_me) {
                    switchContent(mCurrFragment, mMeFragment, MainChannel.ME.name);
                    mCurrFragment = mMeFragment;

                    return true;
                }
                return false;
            }
        });
        if (PluginManager.getInstance(this).getLoadedPlugin("com.fly.tour.news") == null) {
            Toast.makeText(this, "plugin [com.fly.tour.news] not loaded", Toast.LENGTH_SHORT).show();
            return;
        }
        mNewsFragment = Fragment.instantiate(this,"com.fly.tour.news.fragment.MainNewsFragment");

        if (PluginManager.getInstance(this).getLoadedPlugin("com.fly.tour.find") == null) {
            Toast.makeText(this, "plugin [com.fly.tour.find] not loaded", Toast.LENGTH_SHORT).show();
            return;
        }
        mFindFragment = Fragment.instantiate(this,"com.fly.tour.find.fragment.MainFindFragment");

        if (PluginManager.getInstance(this).getLoadedPlugin("com.fly.tour.me") == null) {
            Toast.makeText(this, "plugin [com.fly.tour.me] not loaded", Toast.LENGTH_SHORT).show();
            return;
        }
        mMeFragment = Fragment.instantiate(this,"com.fly.tour.me.fragment.MainMeFragment");

        mCurrFragment = mNewsFragment;
        if (mNewsFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mNewsFragment, MainChannel.NEWS.name).commit();
        }
    }

    @Override
    public void initData() {

    }

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (from == null || to == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.frame_content, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }

    private void loadPlugin(Context base,String apkname) {

        try {
            PluginManager pluginManager = PluginManager.getInstance(base);
            File file = new File(base.getFilesDir(), apkname);
            java.io.InputStream inputStream = base.getAssets().open(apkname, 2);
            java.io.FileOutputStream outputStream = new java.io.FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            inputStream.close();
            pluginManager.loadPlugin(file);
            Log.i(TAG, "Loaded plugin from assets: " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
