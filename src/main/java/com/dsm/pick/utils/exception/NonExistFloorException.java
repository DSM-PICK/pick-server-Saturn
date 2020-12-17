package com.dsm.pick.utils.exception;

public class NonExistFloorException extends RuntimeException {
    public NonExistFloorException() {
        super("층이 1, 2, 3, 4층이 아닙니다.");
    }
}
