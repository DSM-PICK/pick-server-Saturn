package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
