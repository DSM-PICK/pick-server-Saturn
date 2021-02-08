package com.dsm.pick.exception

import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistFloorException(
    floor: Int
) : CommonException(
    code = "NON_EXIST_FLOOR",
    message = "허용하지 않는 층입니다. [floor = ${floor}]",
    status = HttpStatus.BAD_REQUEST,
)