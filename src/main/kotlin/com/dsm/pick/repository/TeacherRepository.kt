package com.dsm.pick.repository

import com.dsm.pick.domain.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository : JpaRepository<Teacher, String>