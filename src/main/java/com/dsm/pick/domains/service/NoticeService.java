package com.dsm.pick.domains.service;

import com.dsm.pick.domains.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<String> getClubNotice() {
        return noticeRepository.findByDate(LocalDateTime.now(), "club");
    }

    public List<String> getMemberNotice() {
        return noticeRepository.findByDate(LocalDateTime.now(), "member");
    }
}
