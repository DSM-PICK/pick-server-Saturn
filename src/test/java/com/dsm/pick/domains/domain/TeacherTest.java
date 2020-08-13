package com.dsm.pick.domains.domain;

import com.dsm.pick.utils.form.TeacherResponseForm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void test() {
        String id = "aaa";
        String pw = "q1w2e3r4";
        String name = "이진혁";
        String refreshToken = "H1H1H1H1H1H1H.H1H1H1H1H1H1H.1H1H1H1H1H1HH1H";

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setPw(pw);
        teacher.setName(name);
        teacher.setRefreshToken(refreshToken);

        String findId = teacher.getId();
        String findPw = teacher.getPw();
        String findName = teacher.getName();
        String findRefreshToken = teacher.getRefreshToken();

        assertEquals(id, findId);
        assertEquals(pw, findPw);
        assertEquals(name, findName);
        assertEquals(refreshToken, findRefreshToken);
    }
}