package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiParam;

public class AttendanceStateRequestForm {

    @ApiParam(value = "학반번호 [ 2417 ]", required = true)
    private String number;
    @ApiParam(value = "교시 [ '7', '8', '9', '10' ]", required = true)
    private int period;
    @ApiParam(value = "출석 상태 [ 출석, 외출, 현체, 병결, 공결, 무단 ]", required = true)
    private String state;

    public AttendanceStateRequestForm() {}
    public AttendanceStateRequestForm(String number, int period, String state) {
        this.number = number;
        this.period = period;
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
