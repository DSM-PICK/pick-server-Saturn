package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class LoginResponseForm {

    @ApiModelProperty(example = "H1H1H1H1H1H.H1H1H1H1H.HH1HH1H1HH1H", required = true)
    private String accessToken;
    @ApiModelProperty(example = "H1H1H1H1H1H.H1H1H1H1H.HH1HH1H1HH1H", required = true)
    private String refreshToken;
    @ApiModelProperty(example = "2003-08-16T21:30:32", required = true)
    private LocalDateTime accessTokenExpiration;

    public LoginResponseForm() {}
    public LoginResponseForm(String accessToken, String refreshToken, LocalDateTime accessTokenExpiration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(LocalDateTime accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }
}
