package com.dsm.pick.utils.form;

import com.dsm.pick.utils.exception.TokenExpirationException;
import io.swagger.annotations.ApiParam;

public class TokenResponseForm {

    @ApiParam(value = "리프레시 토큰", required = true)
    private String token;

    public TokenResponseForm() {}
    public TokenResponseForm(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}