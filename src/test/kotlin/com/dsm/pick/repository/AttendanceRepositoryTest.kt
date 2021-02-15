package com.dsm.pick.repository

import com.dsm.pick.domain.attribute.Floor
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AttendanceRepositoryTest(
    private val attendanceRepository: AttendanceRepository
) {

    @Test
    fun `date, floor, priority 로 Attendance 검색 OK`() {
        val attendance =
            attendanceRepository.findByStudentClubLocationFloorAndStudentClubLocationPriorityAndActivityDate(
                floor = Floor.THREE,
                priority = 0,
                attendanceDate = LocalDate.of(2021, 1, 1),
            )
    }
}