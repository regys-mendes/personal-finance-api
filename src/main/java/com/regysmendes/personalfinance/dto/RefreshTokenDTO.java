package com.regysmendes.personalfinance.dto;

import java.io.Serializable;

public class RefreshTokenDTO implements Serializable {

    private String refreshToken;

    public RefreshTokenDTO(){
    }

    public RefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
