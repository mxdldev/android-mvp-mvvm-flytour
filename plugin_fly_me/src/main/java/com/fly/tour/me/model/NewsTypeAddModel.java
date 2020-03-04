package com.fly.tour.me.model;

import android.content.Context;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.db.dao.NewsTypeDao;
import com.fly.tour.me.contract.NewsTypeAddContract;

/**
 * Description: <NewsTypeAddModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeAddModel extends BaseModel implements NewsTypeAddContract.Model {
    private NewsTypeDao mNewsTypeDao;
    public NewsTypeAddModel(Context context) {
        super(context);
        mNewsTypeDao = new NewsTypeDao(context);
    }

    @Override
    public boolean addNewsType(String type) {
        return mNewsTypeDao.addNewsType(type);
    }
}
