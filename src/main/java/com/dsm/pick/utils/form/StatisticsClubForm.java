package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StatisticsClubForm {

    @ApiModelProperty(example = "7", required = true)
    private String priority;

    @ApiModelProperty(example = "UP", required = true)
    private String clubName;

    private List<StatisticsStudentForm> clubMembers;

    public StatisticsClubForm() {}
    public StatisticsClubForm(String clubName, List<StatisticsStudentForm> clubMembers) {
        this.clubName = clubName;
        this.clubMembers = clubMembers;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<StatisticsStudentForm> getClubMembers() {
        return clubMembers;
    }

    public void setClubMembers(List<StatisticsStudentForm> clubMembers) {
        this.clubMembers = clubMembers;
    }
}
