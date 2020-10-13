package com.dsm.pick.utils.exception;

public class TeacherNameNotFoundException extends RuntimeException {

    public TeacherNameNotFoundException() {
        super("Teacher 의 Name 이 존재하지 않습니다.");
    }

    public TeacherNameNotFoundException(String message) {
        super(message);
    }
}
