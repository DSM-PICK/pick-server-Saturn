package com.dsm.pick.utils.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class NoticeResponseForm {
    @ApiModelProperty(example = "김대웅 학생이 EntryDSM에서 UP(으)로 이동했습니다.", required = true)
    private List<String> clubList;
    @ApiModelProperty(example = "손정우 학생이 자퇴하였습니다.", required = true)
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
