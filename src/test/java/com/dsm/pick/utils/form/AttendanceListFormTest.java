package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceListFormTest {

    @Test
    void test() {
        String sequence = "1";
        String gradeClassNumber = "2417";
        String name = "이진혁";
        AttendanceStateForm state = new AttendanceStateForm();

        AttendanceListForm noArgsForm = new AttendanceListForm();
        noArgsForm.setSequence(sequence);
        noArgsForm.setGradeClassNumber(gradeClassNumber);
        noArgsForm.setName(name);
        noArgsForm.setState(state);

        new AttendanceListForm(sequence, gradeClassNumber, name, state); // 생성 가능

        String findSequence = noArgsForm.getSequence();
        String findGradeClassNumber = noArgsForm.getGradeClassNumber();
        String findName = noArgsForm.getName();
        AttendanceStateForm findState = noArgsForm.getState();

        assertEquals(sequence, findSequence);
        assertEquals(gradeClassNumber, findGradeClassNumber);
        assertEquals(name, findName);
        assertEquals(state, findState);
    }
}