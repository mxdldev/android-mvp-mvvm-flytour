package com.fly.tour.me.model;

import android.content.Context;

import com.fly.tour.api.NewsDetailService;
import com.fly.tour.api.NewsTypeService;
import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.http.RxAdapter;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;
import com.fly.tour.common.mvp.BaseModel;
import com.fly.tour.common.util.DateUtil;
import com.fly.tour.db.dao.NewsDetailDao;
import com.fly.tour.me.contract.NewsDetailAddContract;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Description: <NewsDetailAddModel><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetailAddModel extends BaseModel implements NewsDetailAddContract.Model {
    private NewsTypeService mNewsTypeService;
    private NewsDetailService mNewsDetailService;
    @Inject
    public NewsDetailAddModel(Context context, NewsDetailDao newsDetailDao) {
        super(context);
        mNewsTypeService = RetrofitManager.getInstance().getNewsTypeService();
        mNewsDetailService = RetrofitManager.getInstance().getNewsDetailService();
    }

    @Override
    public Observable<RespDTO<NewsDetail>> addNewsDetail(int type, String title, String content) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setTypeid(type);
        newsDetail.setTitle(title);
        newsDetail.setContent(content);
        newsDetail.setAddtime(DateUtil.formatDate(new Date(), DateUtil.FormatType.yyyyMMddHHmmss));
        return mNewsDetailService.addNewsDetail(RetrofitManager.getInstance().TOKEN, newsDetail)
                .compose(RxAdapter.<RespDTO<NewsDetail>>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }

    @Override
    public Observable<RespDTO<List<NewsType>>> getNewsType() {
        return mNewsTypeService.getListNewsType(RetrofitManager.getInstance().TOKEN)
                .compose(RxAdapter.<RespDTO<List<NewsType>>>bindUntilEvent(getLifecycle()))
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
