package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class PasswordUpdateRequestForm {

    @ApiParam(value = "newpassword1!", required = true)
    private String newPassword;

    @ApiParam(value = "newpassword1!", required = true)
    private String confirmNewPassword;

    public PasswordUpdateRequestForm() {}
    public PasswordUpdateRequestForm(String newPassword, String confirmNewPassword) {
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
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
