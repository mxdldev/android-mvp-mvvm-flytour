package com.fly.tour.news.model;

import android.content.Context;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.dao.NewsDetailDao;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsDetailContract;

/**
 * Description: <NewsDetailModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {

    private final NewsDetailDao mNewsDetailDao;

    public NewsDetailModel(Context context) {
        super(context);
        mNewsDetailDao = new NewsDetailDao(context);
    }

    @Override
    public NewsDetail getNewsDetailById(int id) {
        return mNewsDetailDao.getNewsDetailById(id);
    }
}
