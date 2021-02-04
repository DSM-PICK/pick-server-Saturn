package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistAccountException(
    teacherId: String,
) : CommonException(
    code = "ALREADY_EXIST_ACCOUNT",
    message = "이미 존재하는 계정입니다. [ID = $teacherId]",
    status = HttpStatus.BAD_REQUEST,
)