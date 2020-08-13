package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseFormTest {

    @Test
    void test() {
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        LocalDateTime accessTokenExpiration = LocalDateTime.now();

        LoginResponseForm noArgsForm = new LoginResponseForm();
        noArgsForm.setAccessToken(accessToken);
        noArgsForm.setRefreshToken(refreshToken);
        noArgsForm.setAccessTokenExpiration(accessTokenExpiration);

        new LoginResponseForm(accessToken, refreshToken, accessTokenExpiration); // 생성 가능

        String findAccessToken = noArgsForm.getAccessToken();
        String findRefreshToken = noArgsForm.getRefreshToken();
        LocalDateTime findAccessTokenExpiration = noArgsForm.getAccessTokenExpiration();

        assertEquals(accessToken, findAccessToken);
        assertEquals(refreshToken, findRefreshToken);
        assertEquals(accessTokenExpiration, findAccessTokenExpiration);
    }
}