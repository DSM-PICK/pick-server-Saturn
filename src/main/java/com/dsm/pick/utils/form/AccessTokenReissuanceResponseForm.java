package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class AccessTokenReissuanceResponseForm {

    @ApiModelProperty(example = "H1H1H1H1H1H.H1H1H1H1H.HH1HH1H1HH1H", required = true)
    private String accessToken;

    public AccessTokenReissuanceResponseForm() {}
    public AccessTokenReissuanceResponseForm(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
