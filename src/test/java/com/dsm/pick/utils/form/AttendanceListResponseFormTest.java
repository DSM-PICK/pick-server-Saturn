package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceListResponseFormTest {

    @Test
    void test() {
        String name = "출석";
        String head = "외출";
        List<AttendanceListForm> attendances = new ArrayList<>();

        AttendanceListResponseForm noArgsForm = new AttendanceListResponseForm();
        noArgsForm.setName(name);
        noArgsForm.setHead(head);
        noArgsForm.setAttendances(attendances);

        new AttendanceListResponseForm(name, head, attendances); // 생성 가능

        String findName = noArgsForm.getName();
        String findHead = noArgsForm.getHead();
        List<AttendanceListForm> findAttendances = noArgsForm.getAttendances();

        assertEquals(name, findName);
        assertEquals(head, findHead);
        assertEquals(attendances, findAttendances);
    }
}