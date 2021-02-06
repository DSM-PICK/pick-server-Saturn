package com.dsm.pick.service

import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceNavigationResponse.LocationInformation
import com.dsm.pick.controller.response.AttendanceResponse
import com.dsm.pick.controller.response.AttendanceResponse.StudentState
import com.dsm.pick.controller.response.AttendanceResponse.StudentState.State
import com.dsm.pick.controller.response.AttendanceResponse.StudentState.Memo
import com.dsm.pick.domain.converter.attribute.Floor
import com.dsm.pick.domain.converter.attribute.Schedule
import com.dsm.pick.exception.ActivityNotFoundException
import com.dsm.pick.exception.NonExistPriorityException
import com.dsm.pick.exception.NonExistScheduleException
import com.dsm.pick.repository.ActivityRepository
import com.dsm.pick.repository.AttendanceRepository
import com.dsm.pick.repository.ClassRepository
import com.dsm.pick.repository.ClubRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AttendanceService(
    private val timeService: TimeService,
    private val activityRepository: ActivityRepository,
    private val clubRepository: ClubRepository,
    private val classRepository: ClassRepository,
    private val attendanceRepository: AttendanceRepository,
) {

    fun showAttendanceNavigation(schedule: Schedule, floor: Floor) =
        AttendanceNavigationResponse(
            date = timeService.changeDateToString(),
            dayOfWeek = timeService.getDayOfWeek(),
            teacherName = findTeacherNameBySchedule(schedule, floor),
            schedule = findSchedule().schedule.value,
            locations = createLocationInformation(schedule, floor),
        )

    fun showAttendance(schedule: Schedule, floor: Floor, priority: Int) =
        AttendanceResponse(
            attendances = createAttendance(schedule, floor, priority)?: listOf(),
            clubHead = ,
            locationName = ,
        )

    private fun findTeacherNameBySchedule(
        schedule: Schedule,
        floor: Floor,
        date: LocalDate = LocalDate.now(),
    ) = if (schedule == Schedule.AFTER_SCHOOL) null
        else when(floor) {
            Floor.ONE -> null
            Floor.TWO -> findSchedule(date).secondFloorTeacher.name
            Floor.THREE -> findSchedule(date).thirdFloorTeacher.name
            Floor.FOUR -> findSchedule(date).forthFloorTeacher.name
        }

    private fun findSchedule(date: LocalDate = LocalDate.now()) = activityRepository.findById(date).orElseThrow { ActivityNotFoundException(date) }

    private fun createLocationInformation(schedule: Schedule, floor: Floor): List<LocationInformation> {
        val locationInformation = findLocationInformation(schedule, floor)
        locationInformation.first().done = "done"
        return locationInformation
    }

    private fun findLocationInformation(schedule: Schedule, floor: Floor) =
        when (schedule) {
            Schedule.CLUB -> clubRepository.findByLocationFloor(floor).sortedBy { it.location.priority }.filter { it.students.isNotEmpty() }.map { LocationInformation(it.location.location, it.name, "none", it.location.priority) }
            Schedule.SELF_STUDY, Schedule.AFTER_SCHOOL -> classRepository.findByFloor(floor).sortedBy { it.priority }.filter { it.students.isNotEmpty() }.map { LocationInformation(it.name, it.name, "none", it.priority) }
        }

    private fun createAttendance(schedule: Schedule, floor: Floor, priority: Int) =
        attendanceRepository.findByActivityScheduleAndStudentClubLocationFloorAndStudentClubLocationPriority(
            schedule, floor, priority)
            ?.groupBy { it.student }
            ?.map { (student, attendance) ->
                StudentState(
                    studentNumber = student.number,
                    studentName = student.name,
                    state = State(
                        seven = attendance.firstOrNull { it.period == 7 }?.state,
                        eight = attendance.firstOrNull { it.period == 8 }?.state,
                        nine = attendance.firstOrNull { it.period == 9 }?.state,
                        ten = attendance.firstOrNull { it.period == 10 }?.state,
                    ),
                    memo = Memo(
                        seven = attendance.firstOrNull { it.period == 7 }?.memo,
                        eight = attendance.firstOrNull { it.period == 8 }?.memo,
                        nine = attendance.firstOrNull { it.period == 9 }?.memo,
                        ten = attendance.firstOrNull { it.period == 10 }?.memo,
                    ),
                )
            }

    private fun findHead(schedule: Schedule, floor: Floor, priority: Int) =
        when (schedule) {
            Schedule.CLUB -> clubRepository.findByLocationFloorAndLocationPriority(floor, priority)?.head
            Schedule.SELF_STUDY -> null
            Schedule.AFTER_SCHOOL -> throw NonExistScheduleException(schedule.value)
        }
}