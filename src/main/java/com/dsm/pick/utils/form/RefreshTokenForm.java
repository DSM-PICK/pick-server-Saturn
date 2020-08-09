package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class RefreshTokenForm {
    @ApiParam(value = "리프레시 토큰", required = true)
    private String token;

    public String getToken() {
        return token;
    }

    public void setRefreshToken(String token) {
        this.token = token;
    }
}