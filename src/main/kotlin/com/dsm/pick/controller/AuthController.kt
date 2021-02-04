package com.dsm.pick.controller

import com.dsm.pick.controller.request.TeacherRequest
import com.dsm.pick.controller.response.AccessTokenResponse
import com.dsm.pick.exception.AccountInformationMismatchException
import com.dsm.pick.service.AuthService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: TeacherRequest) =
        authService.login(
            teacherId = request.id,
            teacherPassword = request.pw,
        )

    @PostMapping("/token")
    fun validateToken(@RequestHeader("Authorization") token: String) =
        authService.validateToken(token)

    @GetMapping("/access-token")
    fun recreateToken(@RequestHeader("Authorization") token: String) =
        AccessTokenResponse(authService.recreateAccessToken(token))

//    @PatchMapping("/password")
//    fun changePassword(
//        @RequestHeader("Authorization") token: String,
//        @RequestBody
//    )
}