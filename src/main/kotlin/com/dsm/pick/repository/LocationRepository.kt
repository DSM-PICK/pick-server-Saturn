package com.dsm.pick.repository

import com.dsm.pick.domain.Location
import com.dsm.pick.domain.attribute.Floor
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, String> {
    fun findByFloor(floor: Floor): List<Location>
}