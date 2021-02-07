package com.dsm.pick.exception

import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistPriorityException(
    schedule: Schedule,
    floor: Floor,
    priority: Int,
    location: String = when (schedule) {
        Schedule.CLUB -> "동아리가"
        Schedule.SELF_STUDY, Schedule.AFTER_SCHOOL -> "교실이"
    }
) : CommonException(
    code = "NON_EXIST_PRIORITY",
    message = "일정이 '${schedule.value}'일 때 ${floor.value}층에 우선순위가 ${priority}인 $location 없습니다.",
    status = HttpStatus.BAD_REQUEST,
)