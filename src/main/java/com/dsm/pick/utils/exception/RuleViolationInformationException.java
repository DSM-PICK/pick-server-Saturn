package com.dsm.pick.utils.exception;

public class RuleViolationInformationException extends RuntimeException {
    public RuleViolationInformationException() {
        super("유저의 정보가 규칙을 위반함");
    }
    public RuleViolationInformationException(String message) {
        super(message);
    }
}
