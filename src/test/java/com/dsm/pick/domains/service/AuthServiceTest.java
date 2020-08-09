package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.User;
import com.dsm.pick.domains.repository.UserRepository;
import com.dsm.pick.utils.form.LoginResultForm;
import com.dsm.pick.utils.form.UserForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


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
        User user = new User();
        user.setId("aaa");
        user.setPw("bbb");

        LoginResultForm result = authService.login(user);
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