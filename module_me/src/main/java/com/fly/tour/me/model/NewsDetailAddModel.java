package com.fly.tour.me.model;

import android.content.Context;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.db.dao.NewsDetailDao;
import com.fly.tour.me.contract.NewsDetailAddContract;

import javax.inject.Inject;

/**
 * Description: <NewsDetailAddModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddModel extends BaseModel implements NewsDetailAddContract.Model {
    private NewsDetailDao mNewsDetailDao;
    @Inject
    public NewsDetailAddModel(Context context, NewsDetailDao newsDetailDao) {
        super(context);
        mNewsDetailDao = newsDetailDao;
    }

    @Override
    public boolean addNewsDetail(int type, String title, String content) {
        return mNewsDetailDao.addNewsDetail(type,title,content);
    }
}
