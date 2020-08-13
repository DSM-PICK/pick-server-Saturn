package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceStateRequestFormTest {

    @Test
    void test() {
        String number = "2417";
        String period = "10";
        String state = "무단";

        AttendanceStateRequestForm noArgsForm = new AttendanceStateRequestForm();
        noArgsForm.setNumber(number);
        noArgsForm.setPeriod(period);
        noArgsForm.setState(state);

        new AttendanceStateRequestForm(number, period, state); // 생성 가능

        String findNumber = noArgsForm.getNumber();
        String findPeriod = noArgsForm.getPeriod();
        String findState = noArgsForm.getState();

        assertEquals(number, findNumber);
        assertEquals(period, findPeriod);
        assertEquals(state, findState);
    }
}