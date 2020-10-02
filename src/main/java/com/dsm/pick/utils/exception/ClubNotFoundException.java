package com.dsm.pick.utils.exception;

public class ClubNotFoundException extends RuntimeException {
    public ClubNotFoundException() {
        super("지정된 클럽을 찾을 수 없음");
    }
}
