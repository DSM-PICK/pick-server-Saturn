package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.SchoolClass;

import java.util.List;

public interface ClubCustomRepository {
    List<SchoolClass> findByFloor(int floor);
}
