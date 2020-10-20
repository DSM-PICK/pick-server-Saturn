package com.dsm.pick.domains.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface NoticeCustomRepository {
    List<String> findByDate(LocalDateTime date, String category);
}
