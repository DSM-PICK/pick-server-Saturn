package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>, AttendanceCustomRepository {
}
