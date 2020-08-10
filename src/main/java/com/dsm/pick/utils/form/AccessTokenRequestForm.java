package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class AccessTokenRequestForm {

    @ApiParam(value = "Access Token", required = true)
    private String token;

    public AccessTokenRequestForm() {}
    public AccessTokenRequestForm(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
