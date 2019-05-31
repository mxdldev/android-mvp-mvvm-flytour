package com.fly.tour.news.dagger.model;

import android.content.Context;

import com.fly.tour.common.dagger.mvp.BaseModel;
import com.fly.tour.db.dao.NewsDetailDao;
import com.fly.tour.db.entity.NewsDetail;
import com.fly.tour.news.contract.NewsDetailContract;

import javax.inject.Inject;

/**
 * Description: <NewsDetailModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {
    private NewsDetailDao mNewsDetailDao;

    @Inject
    public NewsDetailModel(Context context,NewsDetailDao detailDao) {
        super(context);
      mNewsDetailDao = detailDao;
    }

    @Override
    public NewsDetail getNewsDetailById(int id) {
        return mNewsDetailDao.getNewsDetailById(id);
    }
}
