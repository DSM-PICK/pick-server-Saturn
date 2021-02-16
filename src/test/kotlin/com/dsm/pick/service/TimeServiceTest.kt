package com.dsm.pick.service

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class TimeServiceTest {
    private val timeService = TimeService()

    @Test
    fun `요일 알아내기 OK`() {
        val monday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 11))
        val tuesday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 12))
        val wednesday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 13))
        val thursday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 14))
        val friday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 15))
        val saturday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 16))
        val sunday = timeService.getDayOfWeek(LocalDate.of(2003, 8, 17))
        timeService.getDayOfWeek()

        assertThat(monday).isEqualTo("월")
        assertThat(tuesday).isEqualTo("화")
        assertThat(wednesday).isEqualTo("수")
        assertThat(thursday).isEqualTo("목")
        assertThat(friday).isEqualTo("금")
        assertThat(saturday).isEqualTo("토")
        assertThat(sunday).isEqualTo("일")
    }

    @Test
    fun `MMdd 형식으로 변경하기 OK`() {
        val date = timeService.changeDateToString(LocalDate.of(2003, 8, 16))
        timeService.changeDateToString()

        assertThat(date).isEqualTo("0816")
    }
}