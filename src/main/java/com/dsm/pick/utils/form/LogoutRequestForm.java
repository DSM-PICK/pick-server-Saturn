package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class LogoutRequestForm {

    @ApiParam(value = "사용자 아이디", required = true)
    private String id;
    @ApiParam(value = "사용자 엑세스 토큰", required = true)
    private String accessToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
