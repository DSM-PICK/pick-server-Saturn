package com.dsm.pick.utils.form;

import com.dsm.pick.utils.exception.TokenExpirationException;
import io.swagger.annotations.ApiParam;

public class RefreshTokenRequestForm {

    @ApiParam(value = "Refresh Token", required = true)
    private String token;

    public RefreshTokenRequestForm() {}
    public RefreshTokenRequestForm(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}