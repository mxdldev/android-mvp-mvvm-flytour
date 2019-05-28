package com.fly.tour.common.manager;

import android.content.Context;

import com.fly.tour.common.R;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.db.dao.NewsDetailDao;
import com.fly.tour.db.dao.NewsTypeDao;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.db.entity.NewsType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description: <NewsDBManager><br>
 * Author:      gxl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDBManager {
    public static final String TAG = NewsDBManager.class.getSimpleName();
    private static NewsDBManager newsDBManager;
    private Context mContext;
    private List<NewsType> mListNewsType;

    private NewsDBManager(Context context) {
        mContext = context;
    }

    public static NewsDBManager getInstance(Context context) {
        if (newsDBManager == null) {
            synchronized (NewsDBManager.class) {
                if (newsDBManager == null) {
                    newsDBManager = new NewsDBManager(context);
                }
            }
        }
        return newsDBManager;
    }

    public void initNewsDB() {
        NewsTypeDao newsTypeDao = new NewsTypeDao(mContext);
        if (newsTypeDao.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<NewsType>>() {
            }.getType();
            mListNewsType = gson.fromJson(getStringByResId(R.raw.news_type), type);
            newsTypeDao.addListNewStype(mListNewsType);
        }
        NewsDetailDao newsDetailDao = new NewsDetailDao(mContext);
        if(newsDetailDao.isEmpty()){
            Gson gson = new Gson();
            Type type = new TypeToken<List<NewsDetail>>() {
            }.getType();
            List<NewsDetail> newsDetailList = gson.fromJson(getStringByResId(R.raw.news_detail), type);
            newsDetailDao.addListNewsDetail(newsDetailList);
        }
    }

    private String getStringByResId(int resid) {
        InputStream inputStream = mContext.getResources().openRawResource(resid);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public List<NewsType> getListNewsType() {
        return mListNewsType;
    }
}
