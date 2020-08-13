package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceStateFormTest {

    @Test
    void test() {
        String seven = "출석";
        String eight = "외출";
        String nine = "공결";
        String ten = "무단";

        AttendanceStateForm noArgsForm = new AttendanceStateForm();
        noArgsForm.setSeven(seven);
        noArgsForm.setEight(eight);
        noArgsForm.setNine(nine);
        noArgsForm.setTen(ten);

        new AttendanceStateForm(seven, eight, nine, ten); // 생성 가능

        String findSeven = noArgsForm.getSeven();
        String findEight = noArgsForm.getEight();
        String findNine = noArgsForm.getNine();
        String findTen = noArgsForm.getTen();

        assertEquals(seven, findSeven);
        assertEquals(eight, findEight);
        assertEquals(nine, findNine);
        assertEquals(ten, findTen);
    }
}