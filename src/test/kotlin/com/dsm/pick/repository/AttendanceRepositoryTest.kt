package com.dsm.pick.repository

import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.State
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class AttendanceRepositoryTest(
    private val attendanceRepository: AttendanceRepository
) {

    @Test
    fun `Club(floor, priority), date 로 출석부 조회 OK`() {
        val attendance =
            attendanceRepository.findByStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(
                floor = Floor.THREE,
                priority = 0,
                attendanceDate = LocalDate.of(2021, 1, 1),
            ).single()

        assertThat(attendance.id).isEqualTo(1)
        assertThat(attendance.activity.date).isEqualTo(LocalDate.of(2021, 1, 1))
        assertThat(attendance.student.number).isEqualTo("3417")
        assertThat(attendance.period).isEqualTo(Period.EIGHT)
        assertThat(attendance.state).isEqualTo(State.ATTENDANCE)
        assertThat(attendance.teacher.id).isEqualTo("teacherId")
    }

    @Test
    fun `없는 Club(floor, priority), date 로 출석부 조회 OK`() {
        assertThat(
            attendanceRepository.findByStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(
                floor = Floor.TWO,
                priority = 10,
                attendanceDate = LocalDate.of(2003, 8, 16),
            )
        ).isEmpty()
    }

    @Test
    fun `Classroom(floor, priority), date 로 출석부 조회 OK`() {
        val attendance =
            attendanceRepository.findByStudentClassroomFloorAndStudentClassroomPriorityAndActivityDate(
                floor = Floor.THREE,
                priority = 0,
                attendanceDate = LocalDate.of(2021, 1, 2),
            ).single()

        assertThat(attendance.id).isEqualTo(2)
        assertThat(attendance.activity.date).isEqualTo(LocalDate.of(2021, 1, 2))
        assertThat(attendance.student.number).isEqualTo("3417")
        assertThat(attendance.period).isEqualTo(Period.EIGHT)
        assertThat(attendance.state).isEqualTo(State.MOVE)
        assertThat(attendance.teacher.id).isEqualTo("teacherId")
    }

    @Test
    fun `없는 Classroom(floor, priority), date 로 출석부 조회 OK`() {
        assertThat(
            attendanceRepository.findByStudentClassroomFloorAndStudentClassroomPriorityAndActivityDate(
                floor = Floor.TWO,
                priority = 10,
                attendanceDate = LocalDate.of(2003, 8, 16),
            )
        ).isEmpty()
    }

    @Test
    fun `student number, period, date 로 출석부 조회 OK`() {
        val attendance =
            attendanceRepository.findByStudentNumberAndPeriodAndActivityDate(
                studentNumber = "3417",
                period = Period.EIGHT,
                attendanceDate = LocalDate.of(2021, 1, 1)
            )!!

        assertThat(attendance.id).isEqualTo(1)
        assertThat(attendance.activity.date).isEqualTo(LocalDate.of(2021, 1, 1))
        assertThat(attendance.student.number).isEqualTo("3417")
        assertThat(attendance.period).isEqualTo(Period.EIGHT)
        assertThat(attendance.state).isEqualTo(State.ATTENDANCE)
        assertThat(attendance.teacher.id).isEqualTo("teacherId")
    }

    @Test
    fun `없는 student number, period, date 로 출석부 조회 OK`() {
        assertThat(
            attendanceRepository.findByStudentNumberAndPeriodAndActivityDate(
                studentNumber = "void",
                period = Period.ONE,
                attendanceDate = LocalDate.of(2003, 8, 16)
            )
        ).isNull()
    }

    @Test
    fun `학년과 날짜로 출석부 조회 OK`() {
        val attendance =
            attendanceRepository.findByActivityDateAndStudentNumberStartsWith(LocalDate.of(2021, 1, 1), "3")
                .single()

        assertThat(attendance.id).isEqualTo(1)
        assertThat(attendance.activity.date).isEqualTo(LocalDate.of(2021, 1, 1))
        assertThat(attendance.student.number).isEqualTo("3417")
        assertThat(attendance.period).isEqualTo(Period.EIGHT)
        assertThat(attendance.state).isEqualTo(State.ATTENDANCE)
        assertThat(attendance.teacher.id).isEqualTo("teacherId")
    }
}