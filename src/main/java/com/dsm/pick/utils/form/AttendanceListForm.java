package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

public class AttendanceListForm {

    @ApiModelProperty(example = "1", required = true)
    private String sequence;
    @ApiModelProperty(example = "2417", required = true)
    private String gradeClassNumber;
    @ApiModelProperty(example = "이진혁", required = true)
    private String name;
    private AttendanceStateForm state;

    public AttendanceListForm() {}
    public AttendanceListForm(String sequence, String gradeClassNumber, String name, AttendanceStateForm state) {
        this.sequence = sequence;
        this.gradeClassNumber = gradeClassNumber;
        this.name = name;
        this.state = state;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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
}
