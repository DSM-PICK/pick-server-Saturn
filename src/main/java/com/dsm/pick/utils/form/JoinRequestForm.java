package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class JoinRequestForm {
    
    @ApiParam(value = "strawberry", required = true)
    private String id;

    @ApiParam(value = "password1!", required = true)
    private String password;

    @ApiParam(value = "password1!", required = true)
    private String confirmPassword;

    @ApiParam(value = "이진혁", required = true)
    private String name;

    public JoinRequestForm() {}
    public JoinRequestForm(String id, String password, String confirmPassword, String name) {
        this.id = id;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
