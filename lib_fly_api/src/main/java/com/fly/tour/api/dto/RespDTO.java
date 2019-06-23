package com.fly.tour.api.dto;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Description: <RespDTO><br>
 * Author:    gxl<br>
 * Date:      2019/2/19<br>
 * Version:    V1.0.0<br>
 * Update:     <br>
 */
public class RespDTO<T> implements Serializable{

    public int code ;
    public String error = "";
    public T data;

    @Override
    public String toString() {
        return "RespDTO{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }
}
