package com.fly.tour.me.model

import android.content.Context
import com.fly.tour.common.mvp.BaseModel
import com.fly.tour.db.dao.NewsTypeDao
import com.fly.tour.db.entity.NewsType
import com.fly.tour.me.contract.NewsTypeListContract

/**
 * Description: <NewsTypeListModel><br>
 * Author:      mxdl<br>
 * Date:        2020/2/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsTypeListModel(context: Context) : BaseModel(context), NewsTypeListContract.Model {
    private val mNewsTypeDao: NewsTypeDao by lazy { NewsTypeDao(context) }
    override fun getListNewsType(): List<NewsType>? {
        return mNewsTypeDao.getListNewsType()
    }

    override fun deleteNewsTypeById(id: Int): Boolean {
        return mNewsTypeDao.deleteNewsType(id)
    }

}