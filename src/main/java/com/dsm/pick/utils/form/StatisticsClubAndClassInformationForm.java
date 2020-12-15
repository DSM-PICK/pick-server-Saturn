package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class StatisticsClubAndClassInformationForm {
    @ApiModelProperty(example = "정보보안 2실", required = true)
    private String location;
    @ApiModelProperty(example = "UP", required = true)
    private String name;
    @ApiModelProperty(example = "9", required = true)
    private String priority;

    public StatisticsClubAndClassInformationForm() {}
    public StatisticsClubAndClassInformationForm(String location, String name, String priority) {
        this.location = location;
        this.name = name;
        this.priority = priority;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
