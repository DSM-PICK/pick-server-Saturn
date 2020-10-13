package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.domains.service.ServerTimeService;
import com.dsm.pick.utils.exception.NonExistFloorException;
import com.dsm.pick.utils.exception.NonExistFloorOrPriorityException;
import com.dsm.pick.utils.exception.TokenInvalidException;
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

    private final AttendanceService attendanceService;
    private final ServerTimeService serverTimeService;
    private final JwtService jwtService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, JwtService jwtService, ServerTimeService serverTimeService) {
        this.attendanceService = attendanceService;
        this.jwtService = jwtService;
        this.serverTimeService = serverTimeService;
    }

    @ApiOperation(value = "출석 페이지 네비게이션 정보", notes = "방과후 교실 정보 및 선생님 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "floor is not in (1, 2, 3, 4)"),
            @ApiResponse(code = 403, message = "token invalid"),
            @ApiResponse(code = 422, message = "today schedule is not club or self-study"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Access Token")
    })
    @GetMapping("/navigation/{floor}")
    public AttendanceNavigationResponseForm attendanceNavigation(
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            HttpServletRequest request) {

        tokenValidation(request.getHeader("Authorization"));

        int floor = 0;
        try {
            floor = Integer.parseInt(floorStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException("floor is not a number");
        }

        List<ClubAndClassInformationForm> clubAndClassInformationForms =
                attendanceService.getNavigationInformation(floor);
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
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "floor is not in (1, 2, 3, 4) or priority is not a number"),
            @ApiResponse(code = 403, message = "token invalid"),
            @ApiResponse(code = 404, message = "activity not found or club not found or attendance not found"),
            @ApiResponse(code = 422, message = "today schedule is not club or self-study"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Access Token")
    })
    @GetMapping("/student-state/{floor}/{priority}")
    public AttendanceListResponseForm attendanceList(
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true) @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true) @PathVariable("priority") String priorityStr,
            HttpServletRequest request) {

        tokenValidation(request.getHeader("Authorization"));

        int floor = 0;
        int priority = 0;
        try {
            floor = Integer.parseInt(floorStr);
            priority = Integer.parseInt(priorityStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorOrPriorityException("floor 또는 priority 가 숫자가 아님");
        }

        Club club =
                attendanceService.getClubHeadAndName(floor, priority);
        List<AttendanceListForm> attendanceList =
                attendanceService.getAttendanceList(LocalDate.now(), floor, priority);

        return new AttendanceListResponseForm(club.getName(), club.getHead(), attendanceList);
    }

    @ApiOperation(value = "출석", notes = "출석 상태 변환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "floor is not in (1, 2, 3, 4) or priority is not a number"),
            @ApiResponse(code = 404, message = "attendance not found"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Access Token")
    })
    @PostMapping("/student-state")
    public void changeAttendanceState(
            AttendanceStateRequestForm attendanceStateRequestForm,
            HttpServletRequest request) {

        tokenValidation(request.getHeader("Authorization"));

        attendanceService.updateAttendance(
                LocalDate.now(),
                attendanceStateRequestForm.getNumber(),
                attendanceStateRequestForm.getPeriod(),
                attendanceStateRequestForm.getState()
        );
    }

    private void tokenValidation(String token) {
        boolean isValid = jwtService.isValid(token);
        boolean isNotTimeOut = jwtService.isNotTimeOut(token);

        try {
            if(!(isValid && isNotTimeOut)) {
                throw new TokenInvalidException("토큰이 잘못되었거나 만료되었습니다.");
            }
        } catch(Exception e) {
            throw new TokenInvalidException("토큰을 검증하는 과정에서 예외가 발생하였습니다.");
        }
    }
}