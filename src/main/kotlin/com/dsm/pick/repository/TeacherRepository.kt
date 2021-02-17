package com.dsm.pick.repository

import com.dsm.pick.domain.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TeacherRepository : JpaRepository<Teacher, String> {
    fun findTeacherById(id: String): Teacher?
}