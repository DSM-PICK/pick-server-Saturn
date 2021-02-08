package com.dsm.pick.exception

import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class ClubNotFoundException(
    floor: Int,
    priority: Int,
) : CommonException(
    code = "CLUB_NOT_FOUND",
    message = "${floor}층의 우선순위가 ${priority}인 동아리를 찾을 수 없습니다.",
    status = HttpStatus.NOT_FOUND,
)