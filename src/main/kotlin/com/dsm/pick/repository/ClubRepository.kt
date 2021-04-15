package com.dsm.pick.repository

import com.dsm.pick.domain.Club
import com.dsm.pick.domain.attribute.Floor
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ClubRepository : JpaRepository<Club, String> {
    @EntityGraph(attributePaths = ["students", "location"])
    fun findByLocationFloor(floor: Floor): List<Club>
    @EntityGraph(attributePaths = ["students", "location"])
    fun findByLocationFloorAndLocationPriority(floor: Floor, priority: Int): Club?
    @EntityGraph(attributePaths = ["location"])
    fun findByTeacher(teacherName: String): List<Club>
}