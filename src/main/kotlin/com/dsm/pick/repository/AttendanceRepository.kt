package com.dsm.pick.repository

import com.dsm.pick.domain.Attendance
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.domain.attribute.State
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface AttendanceRepository : JpaRepository<Attendance, Int> {
    fun findByActivityScheduleAndStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(schedule: Schedule, floor: Floor, priority: Int, attendanceDate: LocalDate): List<Attendance>?
    fun findByStudentNumberAndPeriodAndActivityDate(studentNumber: String, period: Period, attendanceDate: LocalDate): Attendance?
}