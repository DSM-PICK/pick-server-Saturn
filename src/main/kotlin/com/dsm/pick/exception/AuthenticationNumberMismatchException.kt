package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AuthenticationNumberMismatchException(
    authenticationNumber: String,
) : CommonException(
    code = "AUTHENTICATION_NUMBER_MISMATCH",
    message = "인증번호가 불일치합니다. [authentication number = $authenticationNumber]",
    status = HttpStatus.BAD_REQUEST,
)