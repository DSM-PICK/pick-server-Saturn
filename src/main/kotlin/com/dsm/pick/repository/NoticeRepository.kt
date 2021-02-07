package com.dsm.pick.repository

import com.dsm.pick.domain.Notice
import com.dsm.pick.domain.attribute.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface NoticeRepository : JpaRepository<Notice, Int> {
    fun findByCategoryAndDateAfter(category: Category, date: LocalDate): List<Notice>
}