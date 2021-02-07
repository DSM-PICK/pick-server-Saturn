package com.dsm.pick.repository

import com.dsm.pick.domain.Club
import com.dsm.pick.domain.attribute.Floor
import org.springframework.data.jpa.repository.JpaRepository

interface ClubRepository : JpaRepository<Club, String> {
    fun findByLocationFloor(floor: Floor): List<Club>
    fun findByLocationFloorAndLocationPriority(floor: Floor, priority: Int): Club?
}