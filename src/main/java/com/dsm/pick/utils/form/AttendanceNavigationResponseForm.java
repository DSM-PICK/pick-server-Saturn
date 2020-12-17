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

    @ApiModelProperty(example = "club", required = true)
    private String schedule;

    private List<ClubAndClassInformationForm> locations;

    public AttendanceNavigationResponseForm() {}
    public AttendanceNavigationResponseForm(String date, String dayOfWeek, String teacherName, String schedule, List<ClubAndClassInformationForm> locations) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.teacherName = teacherName;
        this.schedule = schedule;
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

    public List<ClubAndClassInformationForm> getLocations() {
        return locations;
    }

    public void setLocations(List<ClubAndClassInformationForm> locations) {
        this.locations = locations;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
