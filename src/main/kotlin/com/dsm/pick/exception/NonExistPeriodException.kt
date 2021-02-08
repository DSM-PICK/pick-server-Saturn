package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistPeriodException(
    period: Int,
) : CommonException(
    code = "NON_EXIST_PERIOD",
    message = "허용하지 않는 교실 [period = ${period}]",
    status = HttpStatus.BAD_REQUEST
)