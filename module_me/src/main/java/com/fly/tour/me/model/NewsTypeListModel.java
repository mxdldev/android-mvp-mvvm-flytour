package com.fly.tour.me.model;

import android.content.Context;

import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.db.dao.NewsTypeDao;
import com.fly.tour.db.entity.NewsType;
import com.fly.tour.me.contract.NewsTypeListContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Description: <NewsTypeListModel><br>
 * Author:      gxl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsTypeListModel extends BaseModel implements NewsTypeListContract.Model {
    public NewsTypeDao mNewsTypeDao;
    @Inject
    public NewsTypeListModel(Context context, NewsTypeDao newsTypeDao) {
        super(context);
        mNewsTypeDao = newsTypeDao;
    }

    @Override
    public List<NewsType> getListNewsType() {
        return mNewsTypeDao.getListNewsType();
    }

    @Override
    public boolean deleteNewsTypeById(int id) {
        return mNewsTypeDao.deleteNewsType(id);
    }
}
