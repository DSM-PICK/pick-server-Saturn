package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistScheduleException(
    schedule: String,
) : CommonException(
    code = "NON_EXIST_SCHEDULE",
    message = "존재하지 않는 일정입니다. [schedule = $schedule]",
    status = HttpStatus.BAD_REQUEST,
)