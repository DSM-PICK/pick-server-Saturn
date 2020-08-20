package com.dsm.pick.domains.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Test
    void save() {
        assertNotNull(entityManager);
    }

    @Test
    void findById() {
    }

    @Test
    void findByRefreshToken() {
    }
}