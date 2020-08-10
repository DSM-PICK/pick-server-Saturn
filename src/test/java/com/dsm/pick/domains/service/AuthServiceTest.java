package com.dsm.pick.domains.service;

import com.dsm.pick.domains.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

class AuthServiceTest {

    @Autowired EntityManager entityManager;
    UserRepository userRepository;
    JwtService jwtService;
    AuthService authService;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepository(entityManager);
        jwtService = new JwtService();
        authService = new AuthService(userRepository, jwtService);
    }

    @Test
    void login() {
        System.out.println(entityManager);
    }

    @Test
    void accessTokenReissuance() {
    }

    @Test
    void logout() {
    }

    @Test
    void join() {
    }
}