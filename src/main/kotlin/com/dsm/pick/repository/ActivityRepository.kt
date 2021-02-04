package com.dsm.pick.repository

import com.dsm.pick.domain.Activity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ActivityRepository : JpaRepository<Activity, LocalDate>