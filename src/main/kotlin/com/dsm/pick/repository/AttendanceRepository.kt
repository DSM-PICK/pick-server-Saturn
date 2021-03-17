package com.dsm.pick.repository

import com.dsm.pick.domain.Attendance
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface AttendanceRepository : JpaRepository<Attendance, Int> {
    fun findByStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(floor: Floor, priority: Int, attendanceDate: LocalDate): List<Attendance>
    fun findByStudentClassroomFloorAndStudentClassroomPriorityAndActivityDate(floor: Floor, priority: Int, attendanceDate: LocalDate): List<Attendance>
    fun findByStudentNumberAndPeriodAndActivityDate(studentNumber: String, period: Period, attendanceDate: LocalDate): Attendance?
    fun findByActivityDateAndStudentNumberStartingWith(date: LocalDate, grade: String): List<Attendance>

    @Query(value = "UPDATE Attendance a SET a.state = :state WHERE a.student.number IN :numbers AND a.period IN :periods AND a.activity.date = :date")
    fun test(
        @Param("state") state: State,
        @Param("numbers") numbers: List<String>,
        @Param("periods") periods: List<Period>,
        @Param("date") date: LocalDate,
    )
}