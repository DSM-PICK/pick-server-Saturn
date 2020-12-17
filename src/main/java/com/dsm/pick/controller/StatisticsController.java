package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.domain.SchoolClass;
import com.dsm.pick.domains.repository.ActivityRepository;
import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.domains.service.ServerTimeService;
import com.dsm.pick.utils.exception.ActivityNotFoundException;
import com.dsm.pick.utils.exception.NonExistFloorException;
import com.dsm.pick.utils.exception.NotClubAndSelfStudyException;
import com.dsm.pick.utils.exception.TokenInvalidException;
import com.dsm.pick.utils.form.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/statistics")
@Api(value = "Statistics Controller")
public class StatisticsController {

    private final static Logger log = LoggerFactory.getLogger(StatisticsController.class);

    private final AttendanceService attendanceService;
    private final ActivityRepository activityRepository;
    private final ServerTimeService serverTimeService;
    private final JwtService jwtService;

    @Autowired
    public StatisticsController(AttendanceService attendanceService, ActivityRepository activityRepository, ServerTimeService serverTimeService, JwtService jwtService) {
        this.attendanceService = attendanceService;
        this.activityRepository = activityRepository;
        this.serverTimeService = serverTimeService;
        this.jwtService = jwtService;
    }

    @ApiOperation(
            value = "일정 통계 페이지 네비게이션 정보",
            notes = "날짜에 해당하는 일정 통계 네비게이션 정보 반환")
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
    @GetMapping("/daily/navigation/{date}/{floor}")
    public StatisticsNavigationResponseForm getDailyStatisticsNavigation(
            HttpServletRequest request,
            @ApiParam(value = "2003-08-16", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable("date") LocalDate date,
            @ApiParam(value = "3", required = true)
            @PathVariable("floor") String floorStr) {

        log.info(String.format("request /statistics/daily/navigation/%s/%s GET",
                date, floorStr));

        tokenValidation(request.getHeader("Authorization"));

        int floor;
        try {
            floor = Integer.parseInt(floorStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException("floor is not a number");
        }

        String schedule = activityRepository.findById(date)
                .orElseThrow(ActivityNotFoundException::new)
                .getSchedule();

        return attendanceService.getStatisticsNavigation(date, schedule, floor);
    }

    @ApiOperation(
            value = "일정 통계 정보",
            notes = "날짜에 해당하는 일정 통계 정보 반환")
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
    @GetMapping("/daily/student-state/{date}/{floor}/{priority}")
    public StatisticsListResponseForm getDailyStatistics(
            HttpServletRequest request,
            @ApiParam(value = "2003-08-16", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable("date") LocalDate date,
            @ApiParam(value = "3", required = true)
            @PathVariable("floor") String floorStr,
            @ApiParam(value = "4", required = true)
            @PathVariable("priority") String priorityStr) {

        log.info(String.format("request /statistics/daily/student-state/%s/%s/%s GET",
                date, floorStr, priorityStr));

        tokenValidation(request.getHeader("Authorization"));

        int floor;
        int priority;
        try {
            floor = Integer.parseInt(floorStr);
            priority = Integer.parseInt(priorityStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException("floor or priority is not a number");
        }

        String schedule = activityRepository.findById(date)
                .orElseThrow(ActivityNotFoundException::new)
                .getSchedule();

        return attendanceService.getStatistics(date, schedule, floor, priority);
    }

    @ApiOperation(
            value = "반별 통계 페이지 네비게이션 정보",
            notes = "날짜에 해당하는 반별 통계 네비게이션 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200,
                    message = "OK!!"),
            @ApiResponse(code = 400,
                    message = "floor is not in (1, 2, 3, 4)"),
            @ApiResponse(code = 403,
                    message = "token invalid"),
            @ApiResponse(code = 422,
                    message = "today schedule is not self-study"),
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
    @GetMapping("/group/navigation/{date}/{floor}")
    public StatisticsNavigationResponseForm getGroupStatisticsNavigation(
            HttpServletRequest request,
            @ApiParam(value = "2003-08-16", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable("date") LocalDate date,
            @ApiParam(value = "3", required = true)
            @PathVariable("floor") String floorStr) {

        log.info(String.format("request /statistics/group/navigation/%s/%s GET",
                date, floorStr));

        tokenValidation(request.getHeader("Authorization"));

        int floor;
        try {
            floor = Integer.parseInt(floorStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException("floor is not a number");
        }

        final String schedule = "self-study";
        return attendanceService.getStatisticsNavigation(date, schedule, floor);
    }

    @ApiOperation(
            value = "일정 통계 정보",
            notes = "날짜에 해당하는 일정 통계 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200,
                    message = "OK!!"),
            @ApiResponse(code = 400,
                    message = "floor is not in (1, 2, 3, 4) or priority is not a number"),
            @ApiResponse(code = 403,
                    message = "token invalid"),
            @ApiResponse(code = 404,
                    message = "club not found or attendance not found"),
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
    @GetMapping("/group/student-state/{date}/{floor}/{priority}")
    public StatisticsListResponseForm getGroupStatistics(
            HttpServletRequest request,
            @ApiParam(value = "2003-08-16", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable("date") LocalDate date,
            @ApiParam(value = "3", required = true)
            @PathVariable("floor") String floorStr,
            @ApiParam(value = "4", required = true)
            @PathVariable("priority") String priorityStr) {

        log.info(String.format("request /statistics/group/student-state/%s/%s/%s GET",
                date, floorStr, priorityStr));

        tokenValidation(request.getHeader("Authorization"));

        int floor;
        int priority;
        try {
            floor = Integer.parseInt(floorStr);
            priority = Integer.parseInt(priorityStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException("floor or priority is not a number");
        }

        final String schedule = "self-study";
        return attendanceService.getStatistics(date, schedule, floor, priority);
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
