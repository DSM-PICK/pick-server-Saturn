package com.dsm.pick.service

import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceNavigationResponse.LocationInformation
import com.dsm.pick.controller.response.AttendanceResponse
import com.dsm.pick.controller.response.AttendanceResponse.StudentState
import com.dsm.pick.controller.response.AttendanceResponse.StudentState.Memo
import com.dsm.pick.domain.Attendance
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.domain.attribute.State
import com.dsm.pick.exception.ActivityNotFoundException
import com.dsm.pick.exception.AttendanceNotFoundException
import com.dsm.pick.exception.NonExistScheduleException
import com.dsm.pick.repository.ActivityRepository
import com.dsm.pick.repository.AttendanceRepository
import com.dsm.pick.repository.ClassroomRepository
import com.dsm.pick.repository.ClubRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class AttendanceService(
    private val timeService: TimeService,
    private val activityRepository: ActivityRepository,
    private val clubRepository: ClubRepository,
    private val classroomRepository: ClassroomRepository,
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
            clubHead = when (schedule) {
                Schedule.CLUB -> findClub(floor, priority)?.head
                Schedule.SELF_STUDY -> null
                Schedule.AFTER_SCHOOL -> throw NonExistScheduleException(schedule.value)
            },
            name = when (schedule) {
                Schedule.CLUB -> findClub(floor, priority)!!.name
                Schedule.SELF_STUDY -> findClassroom(floor, priority)!!.name
                Schedule.AFTER_SCHOOL -> throw NonExistScheduleException(schedule.value)
            },
        )

    fun updateAttendance(
        studentNumber: String,
        period: Period,
        attendanceState: State,
        attendanceDate: LocalDate = LocalDate.now(),
    ) {
        findAttendance(
            studentNumber = studentNumber,
            period = period,
            attendanceDate = attendanceDate,
        ).state = attendanceState
    }

    fun updateMemo(
        studentNumber: String,
        period: Period,
        attendanceMemo: String,
        attendanceDate: LocalDate = LocalDate.now(),
    ) {
        findAttendance(
            studentNumber = studentNumber,
            period = period,
            attendanceDate = attendanceDate,
        ).memo = attendanceMemo
    }

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
            Schedule.SELF_STUDY, Schedule.AFTER_SCHOOL -> classroomRepository.findByFloor(floor).sortedBy { it.priority }.filter { it.students.isNotEmpty() }.map { LocationInformation(it.name, it.name, "none", it.priority) }
        }

    private fun createAttendance(schedule: Schedule, floor: Floor, priority: Int): List<StudentState>? {
        val attendances = attendanceRepository.findByActivityScheduleAndStudentClubLocationFloorAndStudentClubLocationPriority(
                schedule, floor, priority)
            ?.groupBy { it.student }
        attendances?.forEach { (a, b) ->
            println("-------------------------------")
            b.forEach { println(it) }
        }
        return attendances
            ?.map { (student, attendance) ->
                StudentState(
                    studentNumber = student.number,
                    studentName = student.name,
                    state = StudentState.State(
                        seven = attendance.singleOrNull { it.period == Period.SEVEN }?.state?.value,
                        eight = attendance.singleOrNull { it.period == Period.EIGHT }?.state?.value,
                        nine = attendance.singleOrNull { it.period == Period.NINE }?.state?.value,
                        ten = attendance.singleOrNull { it.period == Period.TEN }?.state?.value,
                    ),
                    memo = Memo(
                        seven = attendance.singleOrNull { it.period == Period.SEVEN }?.memo,
                        eight = attendance.singleOrNull { it.period == Period.EIGHT }?.memo,
                        nine = attendance.singleOrNull { it.period == Period.NINE }?.memo,
                        ten = attendance.singleOrNull { it.period == Period.TEN }?.memo,
                    ),
                )
            }
    }
//        attendanceRepository.findByActivityScheduleAndStudentClubLocationFloorAndStudentClubLocationPriority(
//            schedule, floor, priority)
//            ?.groupBy { it.student }
//            ?.map { (student, attendance) ->
//                StudentState(
//                    studentNumber = student.number,
//                    studentName = student.name,
//                    state = StudentState.State(
//                        seven = attendance.singleOrNull { it.period == Period.SEVEN }?.state?.value,
//                        eight = attendance.singleOrNull { it.period == Period.EIGHT }?.state?.value,
//                        nine = attendance.singleOrNull { it.period == Period.NINE }?.state?.value,
//                        ten = attendance.singleOrNull { it.period == Period.TEN }?.state?.value,
//                    ),
//                    memo = Memo(
//                        seven = attendance.singleOrNull { it.period == Period.SEVEN }?.memo,
//                        eight = attendance.singleOrNull { it.period == Period.EIGHT }?.memo,
//                        nine = attendance.singleOrNull { it.period == Period.NINE }?.memo,
//                        ten = attendance.singleOrNull { it.period == Period.TEN }?.memo,
//                    ),
//                )
//            }

    private fun findClub(floor: Floor, priority: Int) =
        clubRepository.findByLocationFloorAndLocationPriority(floor, priority)

    private fun findClassroom(floor: Floor, priority: Int) =
        classroomRepository.findByFloorAndPriority(floor, priority)

    private fun findAttendance(
        studentNumber: String,
        period: Period,
        attendanceDate: LocalDate,
    ) = attendanceRepository.findByStudentNumberAndPeriodAndActivityDate(
            studentNumber = studentNumber,
            period = period,
            attendanceDate = attendanceDate,
        )?: throw AttendanceNotFoundException(studentNumber, period, attendanceDate)
}