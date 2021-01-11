package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class AttendanceMemoForm {

    @ApiModelProperty(example = "메모메모")
    private String sevenMemo;

    @ApiModelProperty(example = "메모메모")
    private String eightMemo;

    @ApiModelProperty(example = "메모메모")
    private String nineMemo;

    @ApiModelProperty(example = "메모메모")
    private String tenMemo;

    public AttendanceMemoForm() {}
    public AttendanceMemoForm(String sevenMemo, String eightMemo, String nineMemo, String tenMemo) {
        this.sevenMemo = sevenMemo;
        this.eightMemo = eightMemo;
        this.nineMemo = nineMemo;
        this.tenMemo = tenMemo;
    }

    public String getSevenMemo() {
        return sevenMemo;
    }

    public void setSevenMemo(String sevenMemo) {
        this.sevenMemo = sevenMemo;
    }

    public String getEightMemo() {
        return eightMemo;
    }

    public void setEightMemo(String eightMemo) {
        this.eightMemo = eightMemo;
    }

    public String getNineMemo() {
        return nineMemo;
    }

    public void setNineMemo(String nineMemo) {
        this.nineMemo = nineMemo;
    }

    public String getTenMemo() {
        return tenMemo;
    }

    public void setTenMemo(String tenMemo) {
        this.tenMemo = tenMemo;
    }
}
