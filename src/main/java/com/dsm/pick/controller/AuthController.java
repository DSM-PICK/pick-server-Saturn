package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.service.AuthService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.exception.TokenInvalidException;
import com.dsm.pick.utils.form.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth Controller")
public class AuthController {

    private final static Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @ApiOperation(value = "로그인", notes = "AccessToken 및 RefreshToken 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "Non Exist ID or Password"),
            @ApiResponse(code = 404, message = "Mismatch ID or Password"),
            @ApiResponse(code = 410, message = "Incorrect Encoding or Algorithm"),
            @ApiResponse(code = 500, message = "500???")
    })
    @PostMapping("/login")
    public LoginResponseForm login(@RequestBody TeacherRequestForm teacherRequestForm) {

        log.info("request /login POST");

        Teacher teacher = new Teacher();
        teacher.setId(teacherRequestForm.getId());
        teacher.setPw(teacherRequestForm.getPw());
        teacher.existIdOrPassword();

        String encodedPassword = authService.encodingPassword(teacher.getPw());
        teacher.setPw(encodedPassword);

        LoginResponseForm result = null;
        if(authService.checkIdAndPassword(teacher)) {
            String teacherId = teacher.getId();

            String accessToken = jwtService.createAccessToken(teacherId);
            String refreshToken = jwtService.createRefreshToken(teacherId);
            String teacherName = authService.getTeacherName(teacherId);

            result = new LoginResponseForm(accessToken, refreshToken, teacherName);
        }

        return result;
    }

    @ApiOperation(value = "정상적인 토큰인지 확인", notes = "정상적인 토큰인지 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 401, message = "Token Expiration"),
            @ApiResponse(code = 403, message = "Token Invalid"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Token")
    })
    @PostMapping("/token")
    public void isUsableToken(HttpServletRequest request) {

        log.info("request /token POST");

        String token = request.getHeader("Authorization");

        boolean isValid = jwtService.isValid(token);
        if(!isValid) {
            throw new TokenInvalidException("토큰이 잘못 되었습니다.");
        }

        boolean isNotTimeOut = jwtService.isNotTimeOut(token);
        if(!isNotTimeOut) {
            throw new TokenExpirationException("토큰이 만료 되었습니다.");
        }
    }

    @ApiOperation(value = "엑세스 토큰 만료", notes = "엑세스 토큰 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 403, message = "Token Invalid"),
            @ApiResponse(code = 404, message = "Mismatch Refresh Token"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Refresh Token")
    })
    @GetMapping("/access-token")
    public AccessTokenReissuanceResponseForm accessTokenReissuance(HttpServletRequest request) {

        log.info("request /access-token GET");

        String refreshToken = request.getHeader("Authorization");

        boolean isValid = jwtService.isValid(refreshToken);
//        boolean isNotTimeOut = jwtService.isNotTimeOut(refreshToken);

        if(isValid) {
            String teacherId = jwtService.getTeacherId(refreshToken);
            String accessToken = jwtService.createAccessToken(teacherId);
            return new AccessTokenReissuanceResponseForm(accessToken);
        } else {
            throw new TokenInvalidException("토큰이 잘못 되었습니다.");
        }
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "Non Exist ID or Password"),
            @ApiResponse(code = 403, message = "Token Invalid"),
            @ApiResponse(code = 404, message = "ID, PASSWORD NOT FOUND"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Refresh Token")
    })
    @PutMapping("/password")
    public void changePassword(HttpServletRequest request, @RequestBody PasswordUpdateRequestForm body) {

        log.info("request /password PUT");

        String accessToken = request.getHeader("Authorization");
        boolean isValid = jwtService.isValid(accessToken);
        if(isValid) {
            Teacher teacher = new Teacher();
            teacher.setId(body.getId());
            teacher.setPw(body.getPassword());
            teacher.existIdOrPassword();

            String encodedPassword = authService.encodingPassword(teacher.getPw());
            teacher.setPw(encodedPassword);

            if(authService.checkIdAndPassword(teacher)) {
                authService.samePassword(body.getNewPassword(), body.getConfirmNewPassword());
                authService.updatePassword(body.getId(), body.getNewPassword());
            }
        } else {
            throw new TokenInvalidException("토큰이 잘못 되었습니다.");
        }
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "유저의 정보가 규칙을 어김"),
            @ApiResponse(code = 403, message = "Token Invalid"),
            @ApiResponse(code = 404, message = "ID or Password Mismatch"),
            @ApiResponse(code = 500, message = "500???")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string", required = true, value = "Refresh Token")
    })
    @PostMapping("/join")
    public void join(HttpServletRequest request, @RequestBody JoinRequestForm form) {

        log.info("request /join POST");

        String accessToken = request.getHeader("Authorization");
        boolean isValid = jwtService.isValid(accessToken);
        if(!isValid)
            throw new TokenInvalidException("토큰이 잘못 되었습니다.");

        authService.samePassword(form.getPassword(), form.getConfirmPassword());
        Teacher teacher = new Teacher(form.getId(), form.getPassword(), form.getName(), form.getOffice());
        authService.join(teacher);
    }
}