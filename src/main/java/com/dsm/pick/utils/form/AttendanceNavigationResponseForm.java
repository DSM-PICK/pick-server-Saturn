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
    @ApiModelProperty(example = "['3학년 1반', '세미나실 2-1', '3학년 2반']", required = true)
    private List<String> locations;

    public AttendanceNavigationResponseForm() {}
    public AttendanceNavigationResponseForm(String date, String dayOfWeek, String teacherName, List<String> locations) {
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

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
