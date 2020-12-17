package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class AttendanceStateForm {

    @ApiModelProperty(example = "출석")
    private String seven;

    @ApiModelProperty(example = "출석", required = true)
    private String eight;

    @ApiModelProperty(example = "출석", required = true)
    private String nine;

    @ApiModelProperty(example = "출석", required = true)
    private String ten;

    public AttendanceStateForm() {}
    public AttendanceStateForm(String seven, String eight, String nine, String ten) {
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
        this.ten = ten;
    }

    public String getSeven() {
        return seven;
    }

    public void setSeven(String seven) {
        this.seven = seven;
    }

    public String getEight() {
        return eight;
    }

    public void setEight(String eight) {
        this.eight = eight;
    }

    public String getNine() {
        return nine;
    }

    public void setNine(String nine) {
        this.nine = nine;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
