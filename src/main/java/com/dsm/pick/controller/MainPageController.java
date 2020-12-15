package com.dsm.pick.controller;

import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.domains.service.NoticeService;
import com.dsm.pick.domains.service.StatisticsService;
import com.dsm.pick.utils.exception.TokenInvalidException;
import com.dsm.pick.utils.form.NoticeResponseForm;
import com.dsm.pick.utils.form.StatisticsClubForm;
import com.dsm.pick.utils.form.StatisticsResponseForm;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/main")
@Api(value = "Main Page Controller")
public class MainPageController {

    private final static Logger log = LoggerFactory.getLogger(MainPageController.class);

    private JwtService jwtService;
    private NoticeService noticeService;
    private StatisticsService statisticsService;

    @Autowired
    public MainPageController(JwtService jwtService, NoticeService noticeService, StatisticsService statisticsService) {
        this.jwtService = jwtService;
        this.noticeService = noticeService;
        this.statisticsService = statisticsService;
    }

    @ApiOperation(value = "공지사항", notes = "최근 2주간의 공지사항 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Access Token")
    })
    @GetMapping("/notice")
    public NoticeResponseForm getNotice(HttpServletRequest request) {

        log.info("request /main/notice GET");

        tokenValidation(request.getHeader("Authorization"));

        List<String> clubNotice = noticeService.getClubNotice();
        List<String> memberNotice = noticeService.getMemberNotice();

        return new NoticeResponseForm(clubNotice, memberNotice);
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
