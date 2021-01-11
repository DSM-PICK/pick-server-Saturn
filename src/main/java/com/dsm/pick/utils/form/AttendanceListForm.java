package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class AttendanceListForm {

    @ApiModelProperty(example = "2417", required = true)
    private String gradeClassNumber;

    @ApiModelProperty(example = "이진혁", required = true)
    private String name;

    private AttendanceStateForm state;
    private AttendanceMemoForm memo;

    public AttendanceListForm() {}
    public AttendanceListForm(String gradeClassNumber, String name, AttendanceStateForm state, AttendanceMemoForm memo) {
        this.gradeClassNumber = gradeClassNumber;
        this.name = name;
        this.state = state;
        this.memo = memo;
    }

    public String getGradeClassNumber() {
        return gradeClassNumber;
    }

    public void setGradeClassNumber(String gradeClassNumber) {
        this.gradeClassNumber = gradeClassNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttendanceStateForm getState() {
        return state;
    }

    public void setState(AttendanceStateForm state) {
        this.state = state;
    }

    public AttendanceMemoForm getMemo() {
        return memo;
    }

    public void setMemo(AttendanceMemoForm memo) {
        this.memo = memo;
    }
}
