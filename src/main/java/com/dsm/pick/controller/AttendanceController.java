package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.domain.SchoolClass;
import com.dsm.pick.domains.repository.ActivityRepository;
import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.domains.service.ServerTimeService;
import com.dsm.pick.utils.exception.*;
import com.dsm.pick.utils.form.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@Api(value = "Attendance Controller")
public class AttendanceController {

    private final static Logger log = LoggerFactory.getLogger(AttendanceController.class);

    private final AttendanceService attendanceService;
    private final ActivityRepository activityRepository;
    private final ServerTimeService serverTimeService;
    private final JwtService jwtService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, ActivityRepository activityRepository, JwtService jwtService, ServerTimeService serverTimeService) {
        this.attendanceService = attendanceService;
        this.activityRepository = activityRepository;
        this.jwtService = jwtService;
        this.serverTimeService = serverTimeService;
    }

    @ApiOperation(value = "출석 페이지 네비게이션 정보", notes = "방과후 교실 정보 및 선생님 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200,
                    message = "OK!!"),
            @ApiResponse(code = 400,
                    message = "floor is not in (1, 2, 3, 4)"),
            @ApiResponse(code = 403,
                    message = "token invalid"),
            @ApiResponse(code = 422,
                    message = "today schedule is not club or self-study"),
            @ApiResponse(code = 500,
                    message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "header",
                    name = "Authorization",
                    dataType = "string",
                    required = true,
                    value = "Access Token")
    })
    @GetMapping("/navigation/{schedule}/{floor}")
    public AttendanceNavigationResponseForm attendanceNavigation(
            @ApiParam(value = "club, self-study", required = true)
            @PathVariable("schedule") String schedule,
            @ApiParam(value = "층[ 1(자습실, 창조실), 2, 3, 4 ]", required = true)
            @PathVariable("floor") String floorStr,
            HttpServletRequest request) {

        log.info(String.format("request /attendance/navigation/%s/%s GET", schedule, floorStr));

        tokenValidation(request.getHeader("Authorization"));

        int floor;
        try {
            floor = Integer.parseInt(floorStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException();
        }

        String findSchedule = activityRepository.findById(LocalDate.now())
                .orElseThrow(ActivityNotFoundException::new)
                .getSchedule();

        if(!(findSchedule.equals("club")
                || findSchedule.equals("self-study")
                || findSchedule.equals("after-school")))
            throw new NotClubAndSelfStudyException();

        List<ClubAndClassInformationForm> clubAndClassInformationForms =
                attendanceService.getNavigationInformation(schedule, floor);
        String date =
                serverTimeService.getMonthAndDate(LocalDate.now());
        String dayOfWeek =
                serverTimeService.getDayOfWeek(LocalDate.now());
        String teacherName =
                attendanceService.getTodayTeacherName(date, floor);

        return new AttendanceNavigationResponseForm(
                date, dayOfWeek, teacherName, schedule, clubAndClassInformationForms);
    }

    @ApiOperation(value = "출석 현황 요청", notes = "출석 현황 반환")
    @ApiResponses({
            @ApiResponse(code = 200,
                    message = "OK!!"),
            @ApiResponse(code = 400,
                    message = "floor is not in (1, 2, 3, 4) or priority is not a number"),
            @ApiResponse(code = 403,
                    message = "token invalid"),
            @ApiResponse(code = 404,
                    message = "activity not found or club not found or attendance not found"),
            @ApiResponse(code = 422,
                    message = "today schedule is not club or self-study"),
            @ApiResponse(code = 500,
                    message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "header",
                    name = "Authorization",
                    dataType = "string",
                    required = true,
                    value = "Access Token")
    })
    @GetMapping("/student-state/{schedule}/{floor}/{priority}")
    public AttendanceListResponseForm attendanceList(
            @ApiParam(value = "club, self-study", required = true)
            @PathVariable("schedule") String schedule,
            @ApiParam(value = "층[ 1(자습실), 2, 3, 4 ]", required = true)
            @PathVariable("floor") String floorStr,
            @ApiParam(value = "위치[ 왼쪽에서부터 0 ]", required = true)
            @PathVariable("priority") String priorityStr,
            HttpServletRequest request) {

        log.info(String.format("request /attendance/student-state/%s/%s/%s GET",
                schedule, floorStr, priorityStr));

        tokenValidation(request.getHeader("Authorization"));

        int floor;
        int priority;
        try {
            floor = Integer.parseInt(floorStr);
            priority = Integer.parseInt(priorityStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorOrPriorityException();
        }

        List<AttendanceListForm> attendanceList =
                attendanceService.getAttendanceList(schedule, LocalDate.now(), floor, priority);

        if(schedule.equals("club")) {
            Club club = attendanceService.getClubHeadAndName(floor, priority);
            return new AttendanceListResponseForm(club.getName(), club.getHead(), attendanceList);
        } else if(schedule.equals("self-study")) {
            SchoolClass schoolClass = attendanceService.getClassName(floor, priority);
            return new AttendanceListResponseForm(schoolClass.getName(), null, attendanceList);
        } else {
            throw new NotClubAndSelfStudyException();
        }
    }

    @ApiOperation(value = "출석", notes = "출석 상태 변환")
    @ApiResponses({
            @ApiResponse(code = 200,
                    message = "OK!!"),
            @ApiResponse(code = 400,
                    message = "floor is not in (1, 2, 3, 4) or priority is not a number"),
            @ApiResponse(code = 404,
                    message = "attendance not found"),
            @ApiResponse(code = 500,
                    message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "header",
                    name = "Authorization",
                    dataType = "string",
                    required = true,
                    value = "Access Token")
    })
    @PatchMapping("/student-state")
    public void changeAttendanceState(
            @RequestBody AttendanceStateRequestForm attendanceStateRequestForm,
            HttpServletRequest request) {

        log.info("request /attendance/student-state PATCH");

        tokenValidation(request.getHeader("Authorization"));

        attendanceService.updateAttendance(
                LocalDate.now(),
                attendanceStateRequestForm.getNumber(),
                attendanceStateRequestForm.getPeriod(),
                attendanceStateRequestForm.getState()
        );
    }

    @ApiOperation(value = "메모", notes = "학생 출석 상태가 이동일 때 메모")
    @ApiResponses({
            @ApiResponse(code = 200,
                    message = "OK!!"),
            @ApiResponse(code = 500,
                    message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "header",
                    name = "Authorization",
                    dataType = "string",
                    required = true,
                    value = "Access Token")
    })
    @PatchMapping("/memo/{student}/{period}")
    public void changeMemo(
            HttpServletRequest request, 
            MemoRequestForm memo,
            @ApiParam(value = "2417", required = true)
            @PathVariable("student") String studentNumber,
            @ApiParam(value = "8", required = true)
            @PathVariable("period") String periodStr) {

        log.info(String.format("request /attendance/memo/%s/%s PATCH",
                studentNumber, periodStr));

        tokenValidation(request.getHeader("Authorization"));

        int period;
        try {
            period = Integer.parseInt(periodStr);
        } catch(NumberFormatException e) {
            throw new NonExistPeriodException();
        }

        attendanceService.updateMemo(LocalDate.now(), studentNumber, period, memo.getMemo());
    }

    private void tokenValidation(String token) {
        boolean isValid = jwtService.isValid(token);
        boolean isNotTimeOut = jwtService.isNotTimeOut(token);

        try {
            if(!(isValid && isNotTimeOut)) {
                throw new TokenInvalidException();
            }
        } catch(Exception e) {
            throw new TokenInvalidException();
        }
    }
}