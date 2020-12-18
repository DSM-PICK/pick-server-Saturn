package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class AttendanceStateForm {

    @ApiModelProperty(example = "출석")
    private String seven;

    @ApiModelProperty(example = "메모메모")
    private String sevenMemo;

    @ApiModelProperty(example = "출석", required = true)
    private String eight;

    @ApiModelProperty(example = "메모메모")
    private String eightMemo;

    @ApiModelProperty(example = "출석", required = true)
    private String nine;

    @ApiModelProperty(example = "메모메모")
    private String nineMemo;

    @ApiModelProperty(example = "출석", required = true)
    private String ten;

    @ApiModelProperty(example = "메모메모")
    private String tenMemo;

    public AttendanceStateForm() {}
    public AttendanceStateForm(String seven, String sevenMemo, String eight, String eightMemo, String nine, String nineMemo, String ten, String tenMemo) {
        this.seven = seven;
        this.sevenMemo = sevenMemo;
        this.eight = eight;
        this.eightMemo = eightMemo;
        this.nine = nine;
        this.nineMemo = nineMemo;
        this.ten = ten;
        this.tenMemo = tenMemo;
    }

    public String getSeven() {
        return seven;
    }

    public void setSeven(String seven) {
        this.seven = seven;
    }

    public String getSevenMemo() {
        return sevenMemo;
    }

    public void setSevenMemo(String sevenMemo) {
        this.sevenMemo = sevenMemo;
    }

    public String getEight() {
        return eight;
    }

    public void setEight(String eight) {
        this.eight = eight;
    }

    public String getEightMemo() {
        return eightMemo;
    }

    public void setEightMemo(String eightMemo) {
        this.eightMemo = eightMemo;
    }

    public String getNine() {
        return nine;
    }

    public void setNine(String nine) {
        this.nine = nine;
    }

    public String getNineMemo() {
        return nineMemo;
    }

    public void setNineMemo(String nineMemo) {
        this.nineMemo = nineMemo;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenMemo() {
        return tenMemo;
    }

    public void setTenMemo(String tenMemo) {
        this.tenMemo = tenMemo;
    }
}
