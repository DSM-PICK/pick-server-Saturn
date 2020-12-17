package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StatisticsNavigationResponseForm {

    @ApiModelProperty(example = "0816", required = true)
    private String date;

    @ApiModelProperty(example = "일", required = true)
    private String dayOfWeek;

    @ApiModelProperty(example = "club", required = true)
    private String schedule;

    private List<StatisticsClubAndClassInformationForm> locations;

    public StatisticsNavigationResponseForm() {}
    public StatisticsNavigationResponseForm(String date, String dayOfWeek, String schedule, List<StatisticsClubAndClassInformationForm> locations) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<StatisticsClubAndClassInformationForm> getLocations() {
        return locations;
    }

    public void setLocations(List<StatisticsClubAndClassInformationForm> locations) {
        this.locations = locations;
    }
}
