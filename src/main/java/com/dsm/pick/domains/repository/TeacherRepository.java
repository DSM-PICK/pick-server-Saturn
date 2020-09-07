package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String>, TeacherCustomRepository {
}
