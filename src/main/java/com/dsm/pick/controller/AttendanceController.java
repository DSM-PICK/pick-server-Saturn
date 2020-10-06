package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.domains.service.ServerTimeService;
import com.dsm.pick.utils.exception.TokenInvalid;
import com.dsm.pick.utils.form.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@Api(value = "Attendance Controller")
public class AttendanceController {

    private AttendanceService attendanceService;
    private ServerTimeService serverTimeService;
    private JwtService jwtService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, JwtService jwtService, ServerTimeService serverTimeService) {
        this.attendanceService = attendanceService;
        this.jwtService = jwtService;
        this.serverTimeService = serverTimeService;
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

        List<ClubAndClassInformationForm> clubAndClassInformationForms =
                attendanceService.getNavigationInformation(activity, floor);
        String date =
                serverTimeService.getMonthAndDate();
        String dayOfWeek =
                serverTimeService.getDayOfWeek();
        String teacherName =
                attendanceService.getTodayTeacherName(date, floor);

        return new AttendanceNavigationResponseForm(date, dayOfWeek, teacherName, clubAndClassInformationForms);
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
    @GetMapping("/{activity}/{floor}/{priority}")
    public AttendanceListResponseForm attendanceList(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("priority") String priorityStr,
            HttpServletRequest request) {

        tokenValidation(request.getHeader("token"));

        int floor = Integer.parseInt(floorStr);
        int priority = Integer.parseInt(priorityStr);

        Club club =
                attendanceService.getClubHeadAndName(activity, floor, priority);
        List<AttendanceListForm> attendanceList =
                attendanceService.getAttendanceList(LocalDate.now(), floor, priority);

        return new AttendanceListResponseForm(club.getName(), club.getHead(), attendanceList);
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
    @PostMapping("/{activity}/{floor}/{priority}")
    public void changeAttendanceState(
            @ApiParam(value = "방과후[ club, self-study ]", required = true) @PathVariable("activity") String activity,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("priority") String priorityStr,
            AttendanceStateRequestForm attendanceStateRequestForm,
            HttpServletRequest request) {

        tokenValidation(request.getHeader("token"));

        int floor = Integer.parseInt(floorStr);
        int priority = Integer.parseInt(priorityStr);

        attendanceService.updateAttendance(
                LocalDate.now(),
                floor,
                priority,
                attendanceStateRequestForm.getNumber(),
                attendanceStateRequestForm.getPeriod(),
                attendanceStateRequestForm.getState()
        );
    }

    private void tokenValidation(String token) {
        boolean isTokenValidation = jwtService.isUsableToken(token);
        try {
            if(!isTokenValidation) {
                throw new TokenInvalid();
            }
        } catch(Exception e) {
            throw new TokenInvalid();
        }
    }
}