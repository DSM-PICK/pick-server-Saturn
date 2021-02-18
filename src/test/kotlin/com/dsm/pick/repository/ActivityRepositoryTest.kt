package com.dsm.pick.repository

import com.dsm.pick.domain.attribute.Schedule
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ActivityRepositoryTest(
    private val activityRepository: ActivityRepository
) {

    @Test
    fun `날짜로 일정 조회 OK`() {
        val activity = activityRepository.findActivityByDate(LocalDate.of(2021, 1, 1))!!

        assertThat(activity).isNotNull
        assertThat(activity.date).isEqualTo(LocalDate.of(2021, 1, 1))
        assertThat(activity.schedule).isEqualTo(Schedule.CLUB)
        assertThat(activity.secondFloorTeacher.id).isEqualTo("teacherId")
        assertThat(activity.thirdFloorTeacher.id).isEqualTo("teacherId")
        assertThat(activity.forthFloorTeacher.id).isEqualTo("teacherId")
    }

    @Test
    fun `없는 날짜로 일정 조회 OK`() {
        assertThat(activityRepository.findActivityByDate(LocalDate.of(2003, 8, 16))).isNull()
    }
}