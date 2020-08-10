package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.service.AuthService;
import com.dsm.pick.utils.form.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth Controller")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "로그인", notes = "JWT 토큰 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login Success"),
            @ApiResponse(code = 404, message = "ID Mismatch OR Password Mismatch"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @PostMapping("/access-refresh-token")
    public LoginResponseForm login(TeacherResponseForm userForm) {
        Teacher teacher = new Teacher();
        teacher.setId(userForm.getId());
        teacher.setPw(userForm.getPw());

        return authService.login(teacher);
    }

    @ApiOperation(value = "엑세스 토큰 만료", notes = "엑세스 토큰 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return Success"),
            @ApiResponse(code = 404, message = "Refresh Token Mismatch"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @PutMapping("/access-token")
    public AccessTokenReissuanceResponseForm accessTokenReissuance(RefreshTokenRequestForm tokenForm) {
        String refreshToken = tokenForm.getToken();
        return authService.accessTokenReissuance(refreshToken);
    }
    
    @ApiOperation(value = "로그아웃", notes = "JWT 토큰 제거")
    @ApiResponses({
            @ApiResponse(code = 200, message = "NO Return"),
            @ApiResponse(code = 404, message = "NOT User"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })

    @DeleteMapping("/access-refresh-token")
    public void logout(AccessTokenRequestForm accessTokenRequestForm) {
        authService.logout(accessTokenRequestForm.getToken());
    }

    @ApiOperation(value = "테스트라고 했다 이거 보고 뭐라 하지 마라", notes = "마마 이거 왜 여노?")
    @PostMapping("/join")
    public void join(Teacher teacher) {
        authService.join(teacher);
    }
}