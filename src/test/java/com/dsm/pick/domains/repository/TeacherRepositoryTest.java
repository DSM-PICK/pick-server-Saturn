package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class TeacherRepositoryTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    TeacherRepository teacherRepository;

    @Test
    void save() {
        Teacher teacher = new Teacher();
        teacher.setId("1234");
        teacher.setPw("1234");
        teacher.setName("1234");
        teacherRepository.save(teacher);
    }

    @Test
    void findById() {
    }

    @Test
    void findByRefreshToken() {
    }
}