package com.dsm.pick.service

import com.dsm.pick.controller.response.*
import com.dsm.pick.controller.response.AttendanceNavigationResponse.LocationInformation
import com.dsm.pick.controller.response.AttendanceResponse.StudentState
import com.dsm.pick.controller.response.AttendanceResponse.StudentState.Memo
import com.dsm.pick.controller.response.StudentSearchResponse.StudentInfo
import com.dsm.pick.domain.Activity
import com.dsm.pick.domain.Attendance
import com.dsm.pick.domain.Location
import com.dsm.pick.domain.attribute.*
import com.dsm.pick.exception.*
import com.dsm.pick.repository.*
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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
    private val teacherRepository: TeacherRepository,
    private val locationRepository: LocationRepository,
) {

//    @Cacheable(value = ["navigation"])
    fun showAttendanceNavigation(schedule: Schedule, floor: Floor, date: LocalDate = LocalDate.now()): AttendanceNavigationResponse {
        val activity = findSchedule(date)
        return AttendanceNavigationResponse(
            date = timeService.changeDateToString(date),
            dayOfWeek = timeService.getDayOfWeek(date),
            teacherName = findTeacherNameBySchedule(schedule, floor, activity),
            schedule = activity.schedule.value,
            locations = createLocationInformation(schedule, floor),
        )
    }

//    @Cacheable(value = ["attendance"])
    fun showAttendance(schedule: Schedule, floor: Floor, priority: Int, date: LocalDate = LocalDate.now()): AttendanceResponse {
        return AttendanceResponse(
            attendances = createAttendance(schedule, floor, priority, date),
            clubHead = when (schedule) {
                Schedule.CLUB -> findClub(floor, priority).head
                Schedule.SELF_STUDY -> null
                Schedule.AFTER_SCHOOL -> null
                Schedule.NO_SCHEDULE -> throw NonExistScheduleException(schedule.value)
            },
            name = when (schedule) {
                Schedule.CLUB -> findClub(floor, priority).name
                Schedule.SELF_STUDY -> findClassroom(floor, priority).name
                Schedule.AFTER_SCHOOL -> "창조실"
                Schedule.NO_SCHEDULE -> throw NonExistScheduleException(schedule.value)
            },
            managerTeacher = when (schedule) {
                Schedule.CLUB -> findClub(floor, priority).teacher
                Schedule.SELF_STUDY -> findNameOfManager(floor, priority)
                Schedule.AFTER_SCHOOL -> null
                Schedule.NO_SCHEDULE -> throw NonExistScheduleException(schedule.value)
            },
        )
    }

//    @CacheEvict(value = ["attendance"], allEntries = true)
    fun updateAttendance(
        studentNumber: String,
        period: Int,
        attendanceState: String,
        attendanceDate: LocalDate = LocalDate.now(),
    ) {
        findAttendance(
            studentNumber = studentNumber,
            period = Period.values().singleOrNull { it.value == period }?: throw NonExistPeriodException(period),
            attendanceDate = attendanceDate,
        ).state = State.values().singleOrNull { it.value == attendanceState }?: throw NonExistStateException(attendanceState)
    }

//    @CacheEvict(value = ["attendance"], allEntries = true)
    fun modifyAllStudent(
        studentNumbers: List<String>,
        periods: List<Int>,
        attendanceState: String,
        attendanceMemo: String,
        attendanceDate: LocalDate = LocalDate.now(),
    ) {
        attendanceRepository.updateByStudentNumbersAndPeriodsAndDate(
            state = State.values().singleOrNull { it.value == attendanceState } ?: throw NonExistStateException(attendanceState),
            memo = attendanceMemo,
            numbers = studentNumbers,
            periods = periods.map { period -> Period.values().singleOrNull { it.value == period } ?: throw NonExistPeriodException(period) },
            date = attendanceDate,
        )
    }

//    @CacheEvict(value = ["attendance"], allEntries = true)
    fun modifyAllStudentState(
        studentNumbers: List<String>,
        periods: List<Int>,
        attendanceState: String,
        attendanceDate: LocalDate = LocalDate.now(),
    ) {
        attendanceRepository.updateByStudentNumbersAndPeriodsAndDate(
            state = State.values().singleOrNull { it.value == attendanceState } ?: throw NonExistStateException(attendanceState),
            numbers = studentNumbers,
            periods = periods.map { period -> Period.values().singleOrNull { it.value == period }?: throw NonExistPeriodException(period) },
            date = attendanceDate,
        )
    }

//    @CacheEvict(value = ["attendance"], allEntries = true)
    fun modifyAllStudentMemo(
        studentNumbers: List<String>,
        periods: List<Int>,
        attendanceMemo: String,
        attendanceDate: LocalDate = LocalDate.now(),
    ) {
        attendanceRepository.updateByStudentNumbersAndPeriodsAndDate(
            memo = attendanceMemo,
            numbers = studentNumbers,
            periods = periods.map { period -> Period.values().singleOrNull { it.value == period } ?: throw NonExistPeriodException(period) },
            date = attendanceDate,
        )
    }

//    @CacheEvict(value = ["attendance"], allEntries = true)
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

//    @Cacheable(value = ["attendance"])
    fun showAttendanceRecordByGrade(
        grade: Grade,
        date: LocalDate = LocalDate.now(),
    ): AttendanceRecordResponse {
        val attendanceByGrade =
            attendanceRepository.findByActivityDateAndStudentNumberStartingWith(
                date = date,
                grade = grade.value.toString(),
            )

        return AttendanceRecordResponse(
            outing = attendanceByGrade.filter { it.state == State.OUTING }.count(),
            fieldExperience = attendanceByGrade.filter { it.state == State.FIELD_EXPERIENCE }.count(),
            homeComing = attendanceByGrade.filter { it.state == State.HOME_COMING }.count(),
            move = attendanceByGrade.filter { it.state == State.MOVE }.count(),
            truancy = attendanceByGrade.filter { it.state == State.TRUANCY }.count(),
        )
    }

//    @Cacheable(value = ["schedule"])
    fun showActivityByDate(date: LocalDate): ActivityResponse {
        val schedule = findSchedule(date)
        return ActivityResponse(
            schedule = schedule.schedule.value,
            secondFloorTeacherName = schedule.secondFloorTeacher.name,
            thirdFloorTeacherName = schedule.thirdFloorTeacher.name,
            forthFloorTeacherName = schedule.forthFloorTeacher.name,
        )
    }

    private fun findTeacherNameBySchedule(
        schedule: Schedule,
        floor: Floor,
        activity: Activity,
    ) = if (schedule == Schedule.AFTER_SCHOOL) null
        else when(floor) {
            Floor.ONE -> null
            Floor.TWO -> activity.secondFloorTeacher.name
            Floor.THREE -> activity.thirdFloorTeacher.name
            Floor.FOUR -> activity.forthFloorTeacher.name
        }

    private fun findSchedule(date: LocalDate = LocalDate.now()) =
        activityRepository.findActivityByDate(date)
            ?: throw ActivityNotFoundException(date)

    private fun createLocationInformation(schedule: Schedule, floor: Floor): List<LocationInformation> {
        if (schedule == Schedule.AFTER_SCHOOL && floor != Floor.ONE) return listOf()
        if (schedule == Schedule.NO_SCHEDULE) return listOf()

        val locationInformation =
            findLocationInformation(schedule, floor)
                .ifEmpty { return listOf() }

        locationInformation.first().done = "done"
        return locationInformation
    }

    private fun findLocationInformation(schedule: Schedule, floor: Floor) =
        when (schedule) {
            Schedule.CLUB -> clubRepository.findByLocationFloor(floor)
                .sortedBy { it.location.priority }
                .filter { it.students.isNotEmpty() }
                .map { LocationInformation(it.location.location, it.name, "none", it.location.priority) }
            Schedule.SELF_STUDY -> classroomRepository.findByFloor(floor)
                .sortedBy { it.priority }
                .filter { it.students.isNotEmpty() }
                .map { LocationInformation(it.name, it.name, "none", it.priority) }
            Schedule.AFTER_SCHOOL -> classroomRepository.findByFloor(floor)
                .sortedBy { it.priority }
                .map { LocationInformation(it.name, it.name, "none", it.priority) }
            Schedule.NO_SCHEDULE -> throw NonExistScheduleException(schedule.value)
        }

    private fun createAttendance(
        schedule: Schedule,
        floor: Floor,
        priority: Int,
        attendanceDate: LocalDate = LocalDate.now(),
    ) = when (schedule) {
        Schedule.CLUB ->
            attendanceRepository.findByStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(
                floor = floor,
                priority = priority,
                attendanceDate = attendanceDate,
            )
        Schedule.SELF_STUDY ->
            attendanceRepository.findByStudentClassroomFloorAndStudentClassroomPriorityAndActivityDate(
                floor = floor,
                priority = priority,
                attendanceDate = attendanceDate,
            )
        Schedule.AFTER_SCHOOL ->
            if (floor == Floor.ONE && priority == 0) {
                attendanceRepository.findByActivityDateAndStudentIsSelfStudy(
                    attendanceDate = attendanceDate,
                    isSelfStudy = true,
                )
            } else listOf()
        Schedule.NO_SCHEDULE -> throw NonExistScheduleException(schedule.value)
    }.groupBy { it.student }
        .map { (student, attendance) ->
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
                reason = attendance.first().reason,
            )
        }

    private fun findClub(floor: Floor, priority: Int) =
        clubRepository.findByLocationFloorAndLocationPriority(floor, priority)
            ?: throw ClubNotFoundException(floor.value, priority)

    private fun findClassroom(floor: Floor, priority: Int) =
        classroomRepository.findByFloorAndPriority(floor, priority)
            ?: throw ClassroomNotFoundException(floor.value, priority)

    private fun findAttendance(
        studentNumber: String,
        period: Period,
        attendanceDate: LocalDate,
    ) = attendanceRepository.findByStudentNumberAndPeriodAndActivityDate(
            studentNumber = studentNumber,
            period = period,
            attendanceDate = attendanceDate,
        )?: throw AttendanceNotFoundException(studentNumber, period.value, attendanceDate)

    private fun findNameOfManager(floor: Floor, priority: Int): String? {
        val classroomManagerId = findClassroom(floor, priority).manager

        return if (classroomManagerId == null) null
        else teacherRepository.findTeacherById(classroomManagerId)?.name
    }

//    @Cacheable(value = ["memoKind"])
    fun getMemoKind(): MemoKindResponse {
        val locations = locationRepository.findAll()
            .groupBy { it.floor }

        return MemoKindResponse(
            twoFloorMemoKind = toMemoKindByFloor(locations, Floor.TWO),
            threeFloorMemoKind = toMemoKindByFloor(locations, Floor.THREE),
            fourFloorMemoKind = toMemoKindByFloor(locations, Floor.FOUR),
        )
    }

    private fun toMemoKindByFloor(locations: Map<Floor, List<Location>>, floor: Floor) =
        locations[floor]
            ?.map { it.shortName }
            ?: listOf()

//    @Cacheable(value = ["attendance"])
    fun getStudentByScheduleAndState(
        state: State,
        schedule: Schedule,
        date: LocalDate = LocalDate.now(),
    ) = when (schedule) {
        Schedule.CLUB ->
            attendanceRepository.findByStateAndActivityDate(state, date)
                .distinctBy { it.student.number }
                .groupBy { it.student.club.location.floor }
        Schedule.SELF_STUDY ->
            attendanceRepository.findByStateAndActivityDate(state, date)
                .distinctBy { it.student.number }
                .groupBy { it.student.classroom.floor }
        Schedule.AFTER_SCHOOL -> throw NonExistScheduleException(schedule.value)
        Schedule.NO_SCHEDULE -> throw NonExistScheduleException(schedule.value)
    }.let {
        StudentSearchResponse(
            twoFloorStudents = toStudentInfo(it, Floor.TWO),
            threeFloorStudents = toStudentInfo(it, Floor.THREE),
            fourFloorStudents = toStudentInfo(it, Floor.FOUR),
        )
    }

    private fun toStudentInfo(it: Map<Floor, List<Attendance>>, floor: Floor) =
        it[floor]
            ?.map { attendance ->
                StudentInfo(
                    studentNumber = attendance.student.number,
                    studentName = attendance.student.name,
                )
            } ?: listOf()
}