package com.dsm.pick.exception

import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus
import java.time.LocalDate

class AttendanceNotFoundException(
    studentNumber: String,
    period: Period,
    attendanceDate: LocalDate,
) : CommonException(
    code = "ATTENDANCE_NOT_FOUND",
    message = "${attendanceDate}에 $studentNumber 학번 학생의 ${period.value}교시 출석부를 찾을 수 없습니다.",
    status = HttpStatus.NOT_FOUND,
)