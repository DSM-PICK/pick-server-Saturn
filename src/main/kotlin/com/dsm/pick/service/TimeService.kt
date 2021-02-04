package com.dsm.pick.service

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TimeService {

    fun changeDateToString(date: LocalDate = LocalDate.now()) = "${date.toString().split("-")[1]}${date.toString().split("-")[2]}"

    fun getDayOfWeek(date: LocalDate = LocalDate.now()) = listOf("월", "화", "수", "목", "금", "토", "일")[date.dayOfWeek.value - 1]
}