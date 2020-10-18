package com.dsm.pick.utils.form;

import java.util.List;

public class NoticeResponseForm {
    private List<String> clubList;
    private List<String> memberList;

    public NoticeResponseForm() {}
    public NoticeResponseForm(List<String> clubList, List<String> memberList) {
        this.clubList = clubList;
        this.memberList = memberList;
    }

    public List<String> getClubList() {
        return clubList;
    }

    public void setClubList(List<String> clubList) {
        this.clubList = clubList;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }
}
