package com.dsm.pick.service

import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceNavigationResponse.LocationInformation
import com.dsm.pick.exception.ActivityNotFoundException
import com.dsm.pick.exception.NonExistFloorException
import com.dsm.pick.repository.ActivityRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AttendanceService(
    private val timeService: TimeService,
    private val activityRepository: ActivityRepository,
) {

    fun showAttendanceNavigation(schedule: String, floor: Int) =
        AttendanceNavigationResponse(
            date = timeService.changeDateToString(),
            dayOfWeek = timeService.getDayOfWeek(),
            teacherName = findTeacherNameBySchedule(
                schedule = schedule,
                floor = floor,
            ),
            schedule = findSchedule().schedule,
            locations = ,
        )

    private fun findTeacherNameBySchedule(
        schedule: String,
        date: LocalDate = LocalDate.now(),
        floor: Int,
    ) = when(floor) {
            1 -> null
            2 -> findSchedule(date).secondFloorTeacher.name
            3 -> findSchedule(date).thirdFloorTeacher.name
            4 -> findSchedule(date).forthFloorTeacher.name
            else -> if (schedule == "after-school") null else throw NonExistFloorException(floor)
        }

    private fun findSchedule(date: LocalDate = LocalDate.now()) = activityRepository.findById(date).orElseThrow { ActivityNotFoundException(date) }

    private fun findLocationInformation(schedule: String, floor: Int) {
        validateFloor(floor)

    }

    private fun validateFloor(floor: Int) = if (floor in 1..4) true else throw NonExistFloorException(floor)
}