package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Activity;
import com.dsm.pick.domains.repository.ActivityRepository;
import com.dsm.pick.domains.service.AttendanceService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.domains.service.ServerTimeService;
import com.dsm.pick.utils.exception.ActivityNotFoundException;
import com.dsm.pick.utils.exception.NonExistFloorException;
import com.dsm.pick.utils.exception.NotClubAndSelfStudyException;
import com.dsm.pick.utils.exception.TokenInvalidException;
import com.dsm.pick.utils.form.AttendanceNavigationResponseForm;
import com.dsm.pick.utils.form.ClubAndClassInformationForm;
import com.dsm.pick.utils.form.StatisticsClubAndClassInformationForm;
import com.dsm.pick.utils.form.StatisticsNavigationResponseForm;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @ApiOperation(value = "일정 통계 페이지 네비게이션 정보", notes = "날짜에 해당하는 통계 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Access Token")
    })
    @GetMapping("/daily/navigation/{date}/{floor}")
    public StatisticsNavigationResponseForm getDailyStatistics(
            HttpServletRequest request,
            @ApiParam(value = "2003-08-16", required = true)
            @PathVariable("date") LocalDate date,
            @ApiParam(value = "3", required = true)
            @PathVariable("floor") String floorStr) {

        log.info("request /statistics/daily/navigation/{floor}/ GET");

        tokenValidation(request.getHeader("Authorization"));

        int floor = 0;
        try {
            floor = Integer.parseInt(floorStr);
        } catch(NumberFormatException e) {
            throw new NonExistFloorException("floor is not a number");
        }

        String schedule = activityRepository.findById(date)
                .orElseThrow(ActivityNotFoundException::new)
                .getSchedule();

        if(!(schedule.equals("club")
                || schedule.equals("self-study")
                || schedule.equals("after-school")))
            throw new NotClubAndSelfStudyException(
                    "today schedule is not club or self-study or after-school");

        List<StatisticsClubAndClassInformationForm> statisticsClubAndClassInformationForms =
                attendanceService.getStatisticsNavigationInformation(schedule, floor);
        String monthAndDate =
                serverTimeService.getMonthAndDate(date);
        String dayOfWeek =
                serverTimeService.getDayOfWeek(date);

        return new StatisticsNavigationResponseForm(
                monthAndDate, dayOfWeek, schedule, statisticsClubAndClassInformationForms);
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
