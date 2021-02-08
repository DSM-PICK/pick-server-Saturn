package com.dsm.pick.exception

import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistPriorityException(
    schedule: String,
    floor: Int,
    priority: Int,
    location: String = when (schedule) {
        Schedule.CLUB.value -> "동아리가"
        Schedule.SELF_STUDY.value, Schedule.AFTER_SCHOOL.value -> "교실이"
        else -> throw NonExistScheduleException(schedule)
    }
) : CommonException(
    code = "NON_EXIST_PRIORITY",
    message = "일정이 '${schedule}'일 때 ${floor}층에 우선순위가 ${priority}인 $location 없습니다.",
    status = HttpStatus.BAD_REQUEST,
)