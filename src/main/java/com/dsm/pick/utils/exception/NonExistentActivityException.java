package com.dsm.pick.utils.exception;

public class NonExistentActivityException extends RuntimeException {
    public NonExistentActivityException() {
        super("Activity 가 club 또는 self-study 가 아닙니다.");
    }
}
