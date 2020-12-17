package com.dsm.pick.utils.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException() {
        super("존재하지 않는 선생님입니다.");
    }
}
