package com.dsm.pick.utils.exception;

public class NotClubAndSelfStudyException extends RuntimeException {
    public NotClubAndSelfStudyException() {
        super("스케쥴이 동아리 또는 자율학습이 아닙니다.");
    }
}
