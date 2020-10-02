package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class ClubAndClassInformationForm {
    @ApiModelProperty(example = "정보보안 2실", required = true)
    private String location;
    @ApiModelProperty(example = "UP", required = true)
    private String name;
    @ApiModelProperty(example = "false", required = true)
    private boolean done;
    @ApiModelProperty(example = "3", required = true)
    private int priority;

    public ClubAndClassInformationForm() {}
    public ClubAndClassInformationForm(String location, String name, boolean done, int priority) {
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
