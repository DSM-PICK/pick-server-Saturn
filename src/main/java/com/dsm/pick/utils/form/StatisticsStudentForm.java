package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class StatisticsStudentForm {

    @ApiModelProperty(example = "2417 이진혁", required = true)
    private String text;
    @ApiModelProperty(example = "무단", required = true)
    private String state;

    public StatisticsStudentForm() {}
    public StatisticsStudentForm(String text, String state) {
        this.text = text;
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
