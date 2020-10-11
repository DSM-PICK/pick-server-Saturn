package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.PreAbsence;

import java.util.List;

public interface PreAbsenceCustomRepository {
    List<PreAbsence> findByCurrentDate();
}
