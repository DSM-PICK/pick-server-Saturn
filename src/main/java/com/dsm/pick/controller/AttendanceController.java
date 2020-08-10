package com.dsm.pick.controller;

import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.utils.form.AccessTokenRequestForm;
import com.dsm.pick.utils.form.AttendanceListResponseForm;
import com.dsm.pick.utils.form.AttendanceNavigationResponseForm;
import com.dsm.pick.utils.form.AttendanceStateRequestForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@Api(value = "Attendance Controller")
public class AttendanceController {

    private AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/{activity}/{floor}")
    public AttendanceNavigationResponseForm attendanceNavigation(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            AccessTokenRequestForm accessTokenRequestForm) {

        int floor = Integer.parseInt(floorStr);             // NumberFormatException

        System.out.println(activity);
        System.out.println(floorStr);
        System.out.println(accessTokenRequestForm.getToken());
        System.out.println("------------------------------------------------------------");

        return new AttendanceNavigationResponseForm();
    }

    @GetMapping("/{activity}/{floor}/{location}")
    public AttendanceListResponseForm attendanceList(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("location") String locationStr,
            AccessTokenRequestForm accessTokenRequestForm) {

        System.out.println(activity);
        System.out.println(floorStr);
        System.out.println(locationStr);
        System.out.println(accessTokenRequestForm.getToken());
        System.out.println("------------------------------------------------------------");

        return new AttendanceListResponseForm();
    }

    @PostMapping("/{activity}/{floor}/{location}")
    public void changeAttendanceState(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("location") String locationStr,
            AttendanceStateRequestForm attendanceStateRequestForm) {

        System.out.println(activity);
        System.out.println(floorStr);
        System.out.println(locationStr);
        System.out.println(attendanceStateRequestForm.getNumber());
        System.out.println(attendanceStateRequestForm.getPeriod());
        System.out.println(attendanceStateRequestForm.getState());
        System.out.println(attendanceStateRequestForm.getToken());
        System.out.println("------------------------------------------------------------");
    }
}