package com.dsm.pick.utils.exception;

public class NonExistPeriodException extends RuntimeException {
    public NonExistPeriodException() {
        super("교시가 7, 8, 9, 10교시가 아닙니다.");
    }
}
