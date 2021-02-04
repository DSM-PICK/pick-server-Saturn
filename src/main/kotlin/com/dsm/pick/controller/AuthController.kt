package com.dsm.pick.controller

import com.dsm.pick.controller.request.TeacherRequest
import com.dsm.pick.exception.AccountInformationMismatchException
import com.dsm.pick.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
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


}