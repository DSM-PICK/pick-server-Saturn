package com.dsm.pick.repository

import com.dsm.pick.domain.Activity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ActivityRepository : JpaRepository<Activity, LocalDate> {
    @EntityGraph(attributePaths = ["secondFloorTeacher", "thirdFloorTeacher", "forthFloorTeacher"])
    fun findActivityByDate(date: LocalDate): Activity?
}