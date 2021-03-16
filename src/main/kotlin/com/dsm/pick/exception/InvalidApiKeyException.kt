package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class InvalidApiKeyException : CommonException(
    code = "INVALID_API_KEY",
    message = "API-KEY 가 잘못되었습니다.",
    status = HttpStatus.BAD_REQUEST,
)