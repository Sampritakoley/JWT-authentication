package com.jwtauthentication.authentication_jwt;

import lombok.Data;

@Data
public class RequestRefreshToken {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
