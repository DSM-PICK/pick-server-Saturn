package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceNavigationResponseFormTest {

    @Test
    void test() {
        String date = "0816";
        String dayOfWeek = "일";
        String teacherName = "김정은";
        List<ClubInformationForm> locations = new ArrayList<>();

        AttendanceNavigationResponseForm noArgsForm = new AttendanceNavigationResponseForm();
        noArgsForm.setDate(date);
        noArgsForm.setDayOfWeek(dayOfWeek);
        noArgsForm.setTeacherName(teacherName);
        noArgsForm.setLocations(locations);

        new AttendanceNavigationResponseForm(date, dayOfWeek, teacherName, locations); // 생성 가능

        String findDate = noArgsForm.getDate();
        String findDayOfWeek = noArgsForm.getDayOfWeek();
        String findTeacherName = noArgsForm.getTeacherName();
        List<ClubInformationForm> findLocations = noArgsForm.getLocations();

        assertEquals(date, findDate);
        assertEquals(dayOfWeek, findDayOfWeek);
        assertEquals(teacherName, findTeacherName);
        assertEquals(locations, findLocations);
    }
}