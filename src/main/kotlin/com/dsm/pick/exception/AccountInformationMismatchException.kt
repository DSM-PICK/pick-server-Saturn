package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AccountInformationMismatchException(
    requestInformation: String,
    findInformation: String,
    code: String = "ACCOUNT_INFORMATION_MISMATCH",
    message: String = "계정 정보가 불일치합니다. [$requestInformation != $findInformation]",
    status: HttpStatus = HttpStatus.NOT_FOUND,
) : CommonException(code, message, status)