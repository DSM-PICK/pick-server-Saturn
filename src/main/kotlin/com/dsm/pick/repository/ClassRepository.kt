package com.dsm.pick.repository

import com.dsm.pick.domain.Classroom
import com.dsm.pick.domain.converter.attribute.Floor
import org.springframework.data.jpa.repository.JpaRepository

interface ClassRepository : JpaRepository<Classroom, String> {
    fun findByManager(teacherId: String): Classroom?
    fun findByFloor(floor: Floor): List<Classroom>
}