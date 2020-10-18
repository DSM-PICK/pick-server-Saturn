package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer>, NoticeCustomRepository {
}
