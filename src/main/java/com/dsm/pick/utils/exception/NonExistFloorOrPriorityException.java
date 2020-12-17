package com.dsm.pick.utils.exception;

public class NonExistFloorOrPriorityException extends RuntimeException {
    public NonExistFloorOrPriorityException() {
        super("층이 1, 2, 3, 4층이 아니거나 우선순위가 알맞지 않습니다.");
    }
}
