package com.dsm.pick.controller

import com.dsm.pick.controller.request.MemoRequest
import com.dsm.pick.controller.request.StudentStateRequest
import com.dsm.pick.controller.response.ActivityResponse
import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceRecordResponse
import com.dsm.pick.controller.response.AttendanceResponse
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Grade
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.service.AttendanceService
import com.dsm.pick.service.AuthService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.validation.Valid

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
        @RequestParam("date", defaultValue = "#{T(java.time.LocalDate).now()}") date: LocalDate,
    ): AttendanceResponse {
        authService.validateToken(token)
        return attendanceService.showAttendance(schedule, floor, priority, date)
    }

    @PatchMapping("/student-state")
    fun changeAttendance(
        @RequestHeader("Authorization") token: String,
        @RequestBody @Valid request: StudentStateRequest,
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
        @RequestBody @Valid request: MemoRequest,
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

    @GetMapping("/record/{grade}")
    fun showAttendanceRecordByGrade(
        @RequestHeader("Authorization") token: String,
        @PathVariable("grade") grade: Grade,
    ): AttendanceRecordResponse {
        authService.validateToken(token)
        return attendanceService.showAttendanceRecordByGrade(grade)
    }

    @GetMapping("/activity/{date}")
    fun showActivityByDate(
        @RequestHeader("Authorization") token: String,
        @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
    ): ActivityResponse {
        authService.validateToken(token)
        return attendanceService.showActivityByDate(date)
    }
}