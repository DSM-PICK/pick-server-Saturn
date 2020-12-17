package com.dsm.pick.utils.exception;

public class WeekendException extends RuntimeException {
    public WeekendException() {
        super("오늘이 주말이라니!!!");
    }
}
