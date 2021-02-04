package com.dsm.pick.repository

import com.dsm.pick.domain.Classroom
import org.springframework.data.jpa.repository.JpaRepository

interface ClassRepository : JpaRepository<Classroom, String> {
    fun findByManager(teacherId: String): Classroom?
}