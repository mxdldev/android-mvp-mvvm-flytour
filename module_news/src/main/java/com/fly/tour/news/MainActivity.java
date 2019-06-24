package com.fly.tour.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fly.tour.news.fragment.MainNewsFragment;
import com.fly.tour.trip.R;
/**
 * Description: <MainActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/5/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,MainNewsFragment.newInstance()).commit();
    }
}
