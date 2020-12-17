package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class AuthenticationNumberRequestForm {

    @ApiParam(value = "authentication-number", required = true)
    private String authenticationNumber;

    public AuthenticationNumberRequestForm() {}
    public AuthenticationNumberRequestForm(String authenticationNumber) {
        this.authenticationNumber = authenticationNumber;
    }

    public String getAuthenticationNumber() {
        return authenticationNumber;
    }

    public void setAuthenticationNumber(String authenticationNumber) {
        this.authenticationNumber = authenticationNumber;
    }
}
