package com.dsm.pick.repository

import com.dsm.pick.domain.Attendance
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.Schedule
import com.dsm.pick.domain.attribute.State
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface AttendanceRepository : JpaRepository<Attendance, Int> {
    fun findByStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(floor: Floor, priority: Int, attendanceDate: LocalDate): List<Attendance>
    @EntityGraph(attributePaths = ["student"])
    fun findByStudentClassroomFloorAndStudentClassroomPriorityAndActivityDate(floor: Floor, priority: Int, attendanceDate: LocalDate): List<Attendance>
    fun findByActivityDateAndStudentIsSelfStudy(attendanceDate: LocalDate, isSelfStudy: Boolean): List<Attendance>
    fun findByStudentNumberAndPeriodAndActivityDate(studentNumber: String, period: Period, attendanceDate: LocalDate): Attendance?
    fun findByActivityDateAndStudentNumberStartingWith(date: LocalDate, grade: String): List<Attendance>
    fun findByStateAndActivityDate(state: State, date: LocalDate): List<Attendance>

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Attendance a SET a.state = :state WHERE a.student.number IN :numbers AND a.period IN :periods AND a.activity.date = :date")
    fun updateByStudentNumbersAndPeriodsAndDate(
        @Param("state") state: State,
        @Param("numbers") numbers: List<String>,
        @Param("periods") periods: List<Period>,
        @Param("date") date: LocalDate,
    )

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Attendance a SET a.memo = :memo WHERE a.student.number IN :numbers AND a.period IN :periods AND a.activity.date = :date")
    fun updateByStudentNumbersAndPeriodsAndDate(
        @Param("memo") memo: String,
        @Param("numbers") numbers: List<String>,
        @Param("periods") periods: List<Period>,
        @Param("date") date: LocalDate,
    )

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Attendance a SET a.state = :state, a.memo = :memo WHERE a.student.number IN :numbers AND a.period IN :periods AND a.activity.date = :date")
    fun updateByStudentNumbersAndPeriodsAndDate(
        @Param("state") state: State,
        @Param("memo") memo: String,
        @Param("numbers") numbers: List<String>,
        @Param("periods") periods: List<Period>,
        @Param("date") date: LocalDate,
    )
}