package com.dsm.pick.controller

import com.dsm.pick.service.AttendanceService
import com.dsm.pick.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class AttendanceController(
    private val authService: AuthService,
    private val attendanceService: AttendanceService,
) {

    @GetMapping("/navigation/{schedule}/{floor}")
    fun showAttendanceNavigation(
        @RequestHeader("Authorization") token: String,
        @PathVariable("schedule") schedule: String,
        @PathVariable("floor") floor: Int,
    ) {
        authService.validateToken(token)
        return attendanceService.showAttendanceNavigation(
            schedule = schedule,
            floor = floor,
        )
    }
}