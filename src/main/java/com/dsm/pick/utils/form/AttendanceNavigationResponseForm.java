package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AttendanceNavigationResponseForm {
    @ApiModelProperty(example = "0816", required = true)
    private String date;
    @ApiModelProperty(example = "일", required = true)
    private String dayOfWeek;
    @ApiModelProperty(example = "김정은", required = true)
    private String teacherName;
    private List<ClubInformationForm> locations;

    public AttendanceNavigationResponseForm() {}
    public AttendanceNavigationResponseForm(String date, String dayOfWeek, String teacherName, List<ClubInformationForm> locations) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.teacherName = teacherName;
        this.locations = locations;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<ClubInformationForm> getLocations() {
        return locations;
    }

    public void setLocations(List<ClubInformationForm> locations) {
        this.locations = locations;
    }
}
