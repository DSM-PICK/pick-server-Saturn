package com.dsm.pick.utils.form;

public class MemoRequestForm {
    private String memo;

    public MemoRequestForm() {}
    public MemoRequestForm(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
