package com.fly.tour.common.event.common;
import com.fly.tour.common.event.BaseEvent;

/**
 * Description: <BaseFragmentEvent><br>
 * Author:      mxdl<br>
 * Date:        2018/4/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class BaseFragmentEvent<T> extends BaseEvent<T> {
    public BaseFragmentEvent(int code) {
        super(code);
    }
}
