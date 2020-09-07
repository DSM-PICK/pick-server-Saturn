package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;

import java.util.Optional;

public interface TeacherCustomRepository {
    Optional<Teacher> findByRefreshToken(String refreshToken);
}
