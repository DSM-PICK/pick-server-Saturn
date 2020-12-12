package com.dsm.pick.utils.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException() {
        super("존재하지 않는 유저입니다.");
    }
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
