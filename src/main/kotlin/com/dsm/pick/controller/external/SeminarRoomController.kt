package com.dsm.pick.controller.external

import com.dsm.pick.controller.request.StudentStateRequest
import com.dsm.pick.service.AttendanceService
import com.dsm.pick.service.external.ExternalValidationService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/external/attendance")
class SeminarRoomController(
    private val externalValidationService: ExternalValidationService,
    private val attendanceService: AttendanceService,
) {

    @PatchMapping("/student-state")
    fun reserveSeminarRoom(
        @RequestHeader("x-api-key") apiKey: String,
        @RequestBody @Valid request: StudentStateRequest,
    ) {
        externalValidationService.validateApiKey(apiKey)
        externalValidationService.validateStudentState(request.state)
        attendanceService.updateAttendance(
            studentNumber = request.number,
            period = request.period,
            attendanceState = request.state,
        )
    }
}