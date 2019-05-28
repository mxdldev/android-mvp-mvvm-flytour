package com.fly.tour.news.model;

import android.content.Context;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.db.NewsDBConfig;
import com.fly.tour.db.dao.NewsDetailDao;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.news.contract.NewsListContract;

import java.util.List;

/**
 * Description: <NewsListModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsListModel extends BaseModel implements NewsListContract.Model {
    private NewsDetailDao mDetailDao;
    public NewsListModel(Context context) {
        super(context);
        mDetailDao = new NewsDetailDao(context);
    }

    @Override
    public List<NewsDetail> getNewsType(int type) {
        return mDetailDao.getListNewsByType(type);
    }
}
