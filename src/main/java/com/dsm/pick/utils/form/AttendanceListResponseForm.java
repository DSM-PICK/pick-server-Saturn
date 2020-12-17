package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AttendanceListResponseForm {

    @ApiModelProperty(example = "UP", required = true)
    private String name;

    @ApiModelProperty(example = "손정우", required = true)
    private String head;

    private List<AttendanceListForm> attendances;

    public AttendanceListResponseForm() {}
    public AttendanceListResponseForm(String name, String head, List<AttendanceListForm> attendances) {
        this.name = name;
        this.head = head;
        this.attendances = attendances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<AttendanceListForm> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceListForm> attendances) {
        this.attendances = attendances;
    }
}