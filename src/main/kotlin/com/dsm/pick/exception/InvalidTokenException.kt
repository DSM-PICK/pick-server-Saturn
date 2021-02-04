package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class InvalidTokenException(token: String) : CommonException(
    code = "INVALID_TOKEN",
    message = "토큰이 잘못되었습니다. [token = $token]",
    status = HttpStatus.UNAUTHORIZED,
)