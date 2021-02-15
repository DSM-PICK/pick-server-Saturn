package com.dsm.pick.controller

import com.dsm.pick.controller.request.AuthenticationNumberRequest
import com.dsm.pick.controller.request.JoinRequest
import com.dsm.pick.controller.request.LoginRequest
import com.dsm.pick.controller.request.PasswordRequest
import com.dsm.pick.controller.response.AccessTokenResponse
import com.dsm.pick.service.AuthService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest) =
        authService.login(
            teacherId = request.id,
            teacherPassword = request.password,
        )

    @PostMapping("/token")
    fun validateToken(@RequestHeader("Authorization") token: String) =
        authService.validateToken(token)

    @GetMapping("/access-token")
    fun recreateToken(@RequestHeader("Authorization") token: String) =
        AccessTokenResponse(authService.recreateAccessToken(token))

    @PatchMapping("/password")
    fun changePassword(
        @RequestHeader("Authorization") token: String,
        @RequestBody @Valid request: PasswordRequest,
    ) = authService.changePassword(token, request.newPassword, request.confirmNewPassword)

    @PostMapping("/authentication-number")
    fun validateAuthenticationNumber(@RequestBody @Valid request: AuthenticationNumberRequest) =
        authService.validateAuthenticationNumber(request.authenticationNumber)

    @PostMapping("/join")
    fun join(@RequestBody @Valid request: JoinRequest) =
        authService.join(
            teacherId = request.id,
            teacherPassword = request.password,
            teacherConfirmPassword = request.confirmPassword,
            teacherName = request.name,
        )
}