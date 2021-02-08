package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class ClassroomNotFoundException(
    floor: Int,
    priority: Int,
) : CommonException(
    code = "CLASS_NOT_FOUND",
    message = "${floor}층의 우선순위가 ${priority}인 교실을 찾을 수 없습니다.",
    status = HttpStatus.NOT_FOUND,
)