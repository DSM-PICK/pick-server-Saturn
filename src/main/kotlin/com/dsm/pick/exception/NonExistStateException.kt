package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistStateException(
    state: String,
) : CommonException(
    code = "NON_EXIST_SCHEDULE",
    message = "허용하지 않는 출석상태입니다. [state = $state]",
    status = HttpStatus.BAD_REQUEST,
)