package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StatisticsResponseForm {
    
    @ApiModelProperty(example = "3층", required = true)
    private String floorText;
    private List<StatisticsClubForm> clubArr;

    public StatisticsResponseForm() {}
    public StatisticsResponseForm(String floorText, List<StatisticsClubForm> clubArr) {
        this.floorText = floorText;
        this.clubArr = clubArr;
    }

    public String getFloorText() {
        return floorText;
    }

    public void setFloorText(String floorText) {
        this.floorText = floorText;
    }

    public List<StatisticsClubForm> getClubArr() {
        return clubArr;
    }

    public void setClubArr(List<StatisticsClubForm> clubArr) {
        this.clubArr = clubArr;
    }
}
