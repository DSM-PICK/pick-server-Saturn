package com.dsm.pick.utils.form;

import com.dsm.pick.controller.AuthController;
import io.swagger.annotations.ApiParam;

public class AuthenticationNumberRequestForm {

    @ApiParam(value = "anjfwjrdjdigkfwlahfmrpTek", required = true)
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
