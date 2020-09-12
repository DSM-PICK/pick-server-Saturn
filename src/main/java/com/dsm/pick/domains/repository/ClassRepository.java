package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<SchoolClass, String>, ClassCustomRepository {
}
