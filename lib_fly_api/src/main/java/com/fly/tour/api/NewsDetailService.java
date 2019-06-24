package com.fly.tour.api;


import com.fly.tour.api.config.API;
import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.news.entity.NewsDetail;
import com.fly.tour.api.newstype.entity.NewsType;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
/**
 * Description: <NewsDetailService><br>
 * Author:      mxdl<br>
 * Date:        2019/6/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface NewsDetailService {
    @POST(API.URL_HOST_NEWS + "/newsdetail/query/all")
    Observable<RespDTO<List<NewsDetail>>> getListNewsDetailByType(@Header("Authorization") String tolen, @Query("typid") int typeid);

    @GET(API.URL_HOST_NEWS + "/newsdetail/{id}/detail")
    Observable<RespDTO<NewsDetail>> getNewsDetailById(@Header("Authorization") String tolen, @Path("id") int id);

    @POST(API.URL_HOST_NEWS + "/newsdetail/save")
    Observable<RespDTO<NewsDetail>> addNewsDetail(@Header("Authorization") String tolen, @Body NewsDetail newsDetail);
}
