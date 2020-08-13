package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherResponseFormTest {

    @Test
    void test() {
        String id = "aaa";
        String pw = "q1w2e3r4";

        TeacherResponseForm noArgsForm = new TeacherResponseForm();
        noArgsForm.setId(id);
        noArgsForm.setPw(pw);

        new TeacherResponseForm(id, pw); // 생성 가능

        String findId = noArgsForm.getId();
        String findPw = noArgsForm.getPw();

        assertEquals(id, findId);
        assertEquals(pw, findPw);
    }
}