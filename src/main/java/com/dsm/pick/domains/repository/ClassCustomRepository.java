package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.SchoolClass;

import java.util.List;

public interface ClassCustomRepository {
    List<SchoolClass> findByFloor(int floor);
    SchoolClass findByFloorAndPriority(int floor, int priority);
}
