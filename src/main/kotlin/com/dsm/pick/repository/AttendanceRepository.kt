package com.dsm.pick.repository

import com.dsm.pick.domain.Attendance
import com.dsm.pick.domain.converter.attribute.Floor
import com.dsm.pick.domain.converter.attribute.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceRepository : JpaRepository<Attendance, Int> {
    fun findByActivityScheduleAndStudentClubLocationFloorAndStudentClubLocationPriority(schedule: Schedule, floor: Floor, priority: Int): List<Attendance>?
}