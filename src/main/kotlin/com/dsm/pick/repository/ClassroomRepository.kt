package com.dsm.pick.repository

import com.dsm.pick.domain.Classroom
import com.dsm.pick.domain.attribute.Floor
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository : JpaRepository<Classroom, String> {
    @EntityGraph(attributePaths = ["students"])
    fun findByFloor(floor: Floor): List<Classroom>

    fun findByManager(teacherId: String): Classroom?
    fun findByFloorAndPriority(floor: Floor, priority: Int): Classroom?
    fun findByName(classroomName: String): Classroom?
}