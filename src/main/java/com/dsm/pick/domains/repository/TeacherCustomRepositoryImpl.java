package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Transactional
public class TeacherCustomRepositoryImpl implements TeacherCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Optional<Teacher> findByRefreshToken(String refreshToken) {
        return entityManager.createQuery("SELECT t FROM Teacher t WHERE t.refreshToken = :token", Teacher.class)
                .setParameter("token", refreshToken)
                .getResultList()
                .stream()
                .findAny();
    }
}
