package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.User;
import com.dsm.pick.domains.service.AuthService;
import com.dsm.pick.utils.form.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
    @PostMapping("/login")
    public LoginResultForm login(UserForm userForm) {
        User user = new User();
        user.setId(userForm.getId());
        user.setPw(userForm.getPw());

        return authService.login(user);
    }

    @ApiOperation(value = "엑세스 토큰 만료", notes = "엑세스 토큰 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return Success"),
            @ApiResponse(code = 404, message = "Refresh Token Mismatch"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @PutMapping("/refresh")
    public AccessTokenReissuanceResultForm refreshToken(RefreshTokenForm refreshTokenForm) {
        String refreshToken = refreshTokenForm.getToken();

        return authService.accessTokenReissuance(refreshToken);
    }
    
    @ApiOperation(value = "로그아웃", notes = "JWT 토큰 제거")
    @ApiResponses({
            @ApiResponse(code = 200, message = "NO Return"),
            @ApiResponse(code = 404, message = "NOT User"),
            @ApiResponse(code = 500, message = "500인데 이거 안 뜰듯")
    })
    @PostMapping("/logout")
    public void logout(LogoutRequestForm logoutRequestForm) {
        authService.logout(logoutRequestForm.getId(), logoutRequestForm.getAccessToken());
    }

    @PostMapping("/join")
    public void join(User user) {
        authService.join(user);
    }
}