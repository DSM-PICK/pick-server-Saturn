package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceCustomRepository {
    List<Attendance> findByDateAndFloorAndPriority(LocalDate date, int floor, int priority);
    Attendance findByDateAndFloorAndPriorityAndNumberAndPeriod(LocalDate date, String number, int period);
}
