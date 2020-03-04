package com.fly.tour.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fly.tour.news.fragment.MainNewsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,MainNewsFragment.newInstance()).commit();
    }
}
