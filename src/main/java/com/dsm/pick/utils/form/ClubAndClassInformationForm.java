package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class ClubAndClassInformationForm {

    @ApiModelProperty(example = "정보보안 2실", required = true)
    private String location;

    @ApiModelProperty(example = "UP", required = true)
    private String name;

    @ApiModelProperty(example = "done", required = true)
    private String done;

    @ApiModelProperty(example = "9", required = true)
    private String priority;

    public ClubAndClassInformationForm() {}
    public ClubAndClassInformationForm(String location, String name, String done, String priority) {
        this.location = location;
        this.name = name;
        this.done = done;
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

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
