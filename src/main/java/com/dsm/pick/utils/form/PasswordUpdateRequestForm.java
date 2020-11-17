package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class PasswordUpdateRequestForm {

    @ApiParam(value = "ididididid", required = true)
    private String id;

    @ApiParam(value = "password1!", required = true)
    private String password;

    @ApiParam(value = "newpassword1!", required = true)
    private String newPassword;

    @ApiParam(value = "newpassword1!", required = true)
    private String confirmNewPassword;

    public PasswordUpdateRequestForm() {}
    public PasswordUpdateRequestForm(String id, String password, String newPassword, String confirmNewPassword) {
        this.id = id;
        this.password = password;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
