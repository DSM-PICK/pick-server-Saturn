package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ActivityRepository extends JpaRepository<Activity, LocalDate>, ActivityCustomRepository {
}
