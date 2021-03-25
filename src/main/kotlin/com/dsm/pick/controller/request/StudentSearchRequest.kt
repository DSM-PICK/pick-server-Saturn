package com.dsm.pick.controller.request

import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.domain.attribute.State
import com.dsm.pick.exception.NonExistFloorException
import com.dsm.pick.exception.NonExistScheduleException
import com.dsm.pick.exception.NonExistStateException

data class StudentSearchRequest(
    val schedule: String,
    val studentState: String,
) {

    fun getSchedule() =
        Schedule
            .values()
            .singleOrNull { it.value == schedule }
            ?: throw NonExistScheduleException(schedule)

    fun getStudentState() =
        State
            .values()
            .singleOrNull { it.value == studentState }
            ?: throw NonExistStateException(studentState)
}