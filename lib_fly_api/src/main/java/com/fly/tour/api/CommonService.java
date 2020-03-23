package com.fly.tour.api;

import com.fly.tour.api.dto.RespDTO;
import com.fly.tour.api.security.Token;
import com.fly.tour.api.user.LoginDTO;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommonService {
    @POST("/userapi/user/login")
    Observable<RespDTO<LoginDTO>> login(@Query("username") String name, @Query("password") String pwd);

    @POST("/oauth/token")
    Observable<Token> getToken(@Header(value = "Authorization") String authorization, @Query("grant_type") String type,
                   @Query("username") String username, @Query("password") String password);
}
