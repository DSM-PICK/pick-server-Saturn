package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus
import java.time.LocalDate

class ActivityNotFoundException(
    date: LocalDate,
) : CommonException(
    code = "ACTIVITY_NOT_FOUND",
    message = "오늘의 일정을 찾을 수 없습니다. [date = $date]",
    status = HttpStatus.NOT_FOUND,
)