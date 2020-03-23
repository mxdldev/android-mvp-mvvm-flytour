package com.fly.tour.api.security;

/**
 * Description: <Token><br>
 * Author:      mxdl<br>
 * Date:        2020/3/19<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Token {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
