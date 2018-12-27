package com.fly.tour.trip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fly.tour.trip.fragment.TripMainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,TripMainFragment.newInstance()).commit();
    }
}
