package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AccountInformationMismatchException(
    requestInformation: String,
    findInformation: String,
) : CommonException(
    code = "ACCOUNT_INFORMATION_MISMATCH",
    message = "계정 정보가 불일치합니다. [$requestInformation != $findInformation]",
    status = HttpStatus.NOT_FOUND,
)