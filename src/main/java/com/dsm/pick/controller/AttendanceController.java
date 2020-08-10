package com.dsm.pick.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
@Api(value = "Attendance Controller")
public class AttendanceController {

    @GetMapping("/{activity}/{floor}")
    public void attendanceNavigation(@ApiParam(value = "오늘 뭐하지", required = true) @PathVariable("activity") String activity, @PathVariable("floor") int floor) {
        
    }
}
