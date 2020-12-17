package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StatisticsListResponseForm {

    @ApiModelProperty(example = "UP", required = true)
    private String name;

    @ApiModelProperty(example = "손정우", required = true)
    private String head;

    @ApiModelProperty(example = "김정은", required = true)
    private String managerTeacher;

    private List<AttendanceListForm> attendances;

    public StatisticsListResponseForm() {}
    public StatisticsListResponseForm(String name, String head, String managerTeacher, List<AttendanceListForm> attendances) {
        this.name = name;
        this.head = head;
        this.managerTeacher = managerTeacher;
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

    public String getManagerTeacher() {
        return managerTeacher;
    }

    public void setManagerTeacher(String managerTeacher) {
        this.managerTeacher = managerTeacher;
    }

    public List<AttendanceListForm> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceListForm> attendances) {
        this.attendances = attendances;
    }
}
