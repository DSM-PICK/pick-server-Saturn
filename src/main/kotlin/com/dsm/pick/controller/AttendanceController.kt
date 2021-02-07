package com.dsm.pick.controller

import com.dsm.pick.controller.request.MemoRequest
import com.dsm.pick.controller.request.StudentStateRequest
import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceResponse
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.service.AttendanceService
import com.dsm.pick.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/attendance")
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
        return attendanceService.showAttendance(schedule, floor, priority)
    }

    @PatchMapping("/student-state")
    fun changeAttendance(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: StudentStateRequest,
    ) {
        authService.validateToken(token)
        attendanceService.updateAttendance(
            studentNumber = request.number,
            period = request.period,
            attendanceState = request.state,
        )
    }

    @PatchMapping("/memo/{student}/{period}")
    fun changeMemo(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: MemoRequest,
        @PathVariable("student") studentNumber: String,
        @PathVariable("period") period: Period,
    ) {
        authService.validateToken(token)
        attendanceService.updateMemo(
            studentNumber = studentNumber,
            period = period,
            attendanceMemo = request.memo,
        )
    }
}