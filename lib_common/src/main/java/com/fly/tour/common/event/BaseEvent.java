package com.fly.tour.common.event;

/**
 * Description: <BaseEvent><br>
 * Author:      mxdl<br>
 * Date:        2018/4/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class BaseEvent<T> {
    private int code;
    private T data;

    public BaseEvent(int code) {
        this.code = code;
    }

    public BaseEvent(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
