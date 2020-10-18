package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Notice;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeCustomRepository {
    List<String> findByDate(LocalDateTime date, String category);
}
