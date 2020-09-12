package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClubInformationFormTest {

    @Test
    void test() {
        String location = "정보보안 2실";
        String name = "UP";
        boolean done = false;

        ClubInformationForm noArgsForm = new ClubInformationForm();
        noArgsForm.setLocation(location);
        noArgsForm.setName(name);
        noArgsForm.setDone(done);

        new ClubInformationForm(location, name, done); // 생성 가능

        String findLocation = noArgsForm.getLocation();
        String findName = noArgsForm.getName();
        boolean findDone = noArgsForm.isDone();

        assertEquals(location, findLocation);
        assertEquals(name, findName);
        assertEquals(done, findDone);
    }
}