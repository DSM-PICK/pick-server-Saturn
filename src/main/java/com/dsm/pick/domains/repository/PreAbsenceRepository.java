package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.PreAbsence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreAbsenceRepository extends JpaRepository<PreAbsence, Integer>, PreAbsenceCustomRepository {
}
