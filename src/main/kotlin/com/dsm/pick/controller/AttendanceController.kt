package com.dsm.pick.controller

import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceResponse
import com.dsm.pick.domain.converter.attribute.Floor
import com.dsm.pick.domain.converter.attribute.Schedule
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
        @PathVariable("schedule") schedule: Schedule,
        @PathVariable("floor") floor: Floor,
    ): AttendanceNavigationResponse {
        authService.validateToken(token)
        return attendanceService.showAttendanceNavigation(schedule, floor)
    }

    @GetMapping("/student-state/{schedule}/{floor}/{priority}")
    fun showAttendance(
        @RequestHeader("Authorization") token: String,
        @PathVariable("schedule") schedule: Schedule,
        @PathVariable("floor") floor: Floor,
        @PathVariable("priority") priority: Int,
    ): AttendanceResponse {
        authService.validateToken(token)
        attendanceService.showAttendance(schedule, floor, priority)
    }
}