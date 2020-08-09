package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserRepository {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public Optional<User> findByRefreshToken(String refreshToken) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.refreshToken = :a", User.class)
                .setParameter("a", refreshToken)
                .getResultList()
                .stream()
                .findAny();
    }
}
