package com.fly.tour.api.user;

import com.fly.tour.api.user.entity.User;
/**
 * Description: <LoginDTO><br>
 * Author:      mxdl<br><br>
 * Date:      2019/2/19<br>
 * Version:    V1.0.0<br>
 * Update:     <br>
 */
public class LoginDTO {
    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
