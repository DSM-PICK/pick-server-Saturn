package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class LoginResponseForm {

    @ApiModelProperty(example = "H1H1H1H1H1H.H1H1H1H1H.HH1HH1H1HH1H", required = true)
    private String accessToken;

    @ApiModelProperty(example = "H1H1H1H1H1H.H1H1H1H1H.HH1HH1H1HH1H", required = true)
    private String refreshToken;

    @ApiModelProperty(example = "김정은", required = true)
    private String teacherName;

    private String managedClassroom;

    public LoginResponseForm() {}
    public LoginResponseForm(String accessToken, String refreshToken, String teacherName, String managedClassroom) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.teacherName = teacherName;
        this.managedClassroom = managedClassroom;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getManagedClassroom() {
        return managedClassroom;
    }

    public void setManagedClassroom(String managedClassroom) {
        this.managedClassroom = managedClassroom;
    }
}
