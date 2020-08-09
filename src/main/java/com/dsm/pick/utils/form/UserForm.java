package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class UserForm {

    @ApiParam(value = "사용자 아이디", required = true)
    private String id;
    @ApiParam(value = "사용자 비밀번호", required = true)
    private String pw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}