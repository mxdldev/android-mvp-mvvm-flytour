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

public interface NewsTypeService {
    @POST("/newsapi/newstype/query/all")
    Observable<RespDTO<List<NewsType>>> getListNewsType();

    @GET("/newsapi/newstype/{id}/delete")
    Observable<RespDTO> deleteNewsTypeById(@Path("id") int id);

    @POST("/newsapi/newstype/save")
    Observable<RespDTO<NewsType>> addNewsType(@Body NewsType type);
}
