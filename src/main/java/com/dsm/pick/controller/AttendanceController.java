package com.dsm.pick.controller;

import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.form.AttendanceListResponseForm;
import com.dsm.pick.utils.form.AttendanceNavigationResponseForm;
import com.dsm.pick.utils.form.AttendanceStateRequestForm;
import com.dsm.pick.utils.form.ClubInformationForm;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@Api(value = "Attendance Controller")
public class AttendanceController {

    private AttendanceService attendanceService;
    private JwtService jwtService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, JwtService jwtService) {
        this.attendanceService = attendanceService;
        this.jwtService = jwtService;
    }

    @ApiOperation(value = "출석 페이지 네비게이션 정보", notes = "방과후 교실 정보 및 선생님 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return Success"),
            @ApiResponse(code = 404, message = "NOT User"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "string", required = true, value = "Access Token")
    })
    @GetMapping("/{activity}/{floor}")
    public AttendanceNavigationResponseForm attendanceNavigation(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            HttpServletRequest request) {

        tokenValidation(request.getHeader("token"));

        int floor = Integer.parseInt(floorStr);

        List<ClubInformationForm> clubInformationForms = attendanceService.getNavigationInformation(activity, floor);


        return new AttendanceNavigationResponseForm();
    }

    @ApiOperation(value = "출석 현황 요청", notes = "출석 현황 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return Success"),
            @ApiResponse(code = 404, message = "NOT User"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "string", required = true, value = "Access Token")
    })
    @GetMapping("/{activity}/{floor}/{location}")
    public AttendanceListResponseForm attendanceList(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("location") String locationStr,
            HttpServletRequest request) {

        System.out.println(activity);
        System.out.println(floorStr);
        System.out.println(locationStr);
        System.out.println(request.getHeader("token"));
        System.out.println("------------------------------------------------------------");

        return new AttendanceListResponseForm();
    }

    @ApiOperation(value = "출석", notes = "출석 상태 변환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return Success"),
            @ApiResponse(code = 404, message = "NOT User"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "string", required = true, value = "Access Token")
    })
    @PostMapping("/{activity}/{floor}/{location}")
    public void changeAttendanceState(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("location") String locationStr,
            AttendanceStateRequestForm attendanceStateRequestForm,
            HttpServletRequest request) {

        System.out.println(activity);
        System.out.println(floorStr);
        System.out.println(locationStr);
        System.out.println(attendanceStateRequestForm.getNumber());
        System.out.println(attendanceStateRequestForm.getPeriod());
        System.out.println(attendanceStateRequestForm.getState());
        System.out.println(request.getHeader("token"));
        System.out.println("------------------------------------------------------------");
    }

    private void tokenValidation(String token) {
        boolean isTokenValidation = jwtService.isUsableToken(token);
        try {
            if(!isTokenValidation) {
                throw new TokenExpirationException();
            }
        } catch(Exception e) {
            throw new TokenExpirationException();
        }
    }
}