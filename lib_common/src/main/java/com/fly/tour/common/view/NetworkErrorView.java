package com.fly.tour.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.fly.tour.common.R;


/**
 * Description: <NetworkErrorView><br>
 * Author:      mxdl<br>
 * Date:        2018/6/20<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NetworkErrorView extends RelativeLayout {

    public NetworkErrorView(Context context) {
        super(context);
        init();
    }

    public NetworkErrorView(Context context, int layoutID) {
        super(context);
        init();
    }

    public NetworkErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.button_network_err, this, true);
        this.setClickable(true);
        this.setFocusable(true);
    }
}
