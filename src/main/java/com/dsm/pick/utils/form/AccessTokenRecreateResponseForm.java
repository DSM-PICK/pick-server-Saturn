package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class AccessTokenRecreateResponseForm {

    @ApiModelProperty(example = "H1H1H1H1H1H.H1H1H1H1H.HH1HH1H1HH1H", required = true)
    private String accessToken;

    public AccessTokenRecreateResponseForm() {}
    public AccessTokenRecreateResponseForm(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
