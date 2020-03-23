package com.fly.tour.api;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fly.tour.api.config.API;
import com.fly.tour.api.util.SSLContextUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: <RetrofitManager><br>
 * Author:      mxdl<br>
 * Date:        2019/6/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RetrofitManager {
    public static RetrofitManager retrofitManager;
    public static Context mContext;
    private Retrofit mRetrofit;
    private OkHttpClient.Builder okHttpBuilder;
    private String mToken;

    private RetrofitManager() {
        //给client的builder添加了一个日志拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(20 * 1000, TimeUnit.MILLISECONDS).
                readTimeout(20 * 1000, TimeUnit.MILLISECONDS).
                writeTimeout(20 * 1000, TimeUnit.MILLISECONDS);

        okHttpBuilder.addInterceptor(logging);
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!TextUtils.isEmpty(mToken)) {
                        //Request.Builder requestBuilder = request.newBuilder()
                        //.header("Authorization", "Bearer " + mToken);
                        //request = requestBuilder.build();
                    Request.Builder requestBuilder = request.newBuilder();
                    HttpUrl.Builder urlBuilder = request.url().newBuilder().addQueryParameter("access_token", mToken);
                    request = requestBuilder.url(urlBuilder.build()).build();
                }
                return chain.proceed(request);
            }
        });

        //给client的builder添加了一个socketFactory
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            okHttpBuilder.sslSocketFactory(socketFactory);
        }
        okHttpBuilder.hostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);

        //创建client
        OkHttpClient okHttpClient = okHttpBuilder.build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API.URL_HOST_USER)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static void init(Application application) {
        mContext = application;
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    /**
     * 创建一个公共服务
     *
     * @return
     */
    public CommonService getCommonService() {
        return mRetrofit.create(CommonService.class);
    }

    /**
     * 创建一个新闻类型服务
     *
     * @return
     */
    public NewsTypeService getNewsTypeService() {
        return mRetrofit.create(NewsTypeService.class);
    }

    /**
     * 创建一个新闻详情服务
     *
     * @return
     */
    public NewsDetailService getNewsDetailService() {
        return mRetrofit.create(NewsDetailService.class);
    }

    public void addToken(String token) {
        mToken = token;
    }
}