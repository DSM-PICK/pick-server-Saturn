package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, String>, ClubCustomRepository {
}
