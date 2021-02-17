package com.dsm.pick.service

import com.dsm.pick.controller.response.NoticeResponse
import com.dsm.pick.domain.attribute.Category
import com.dsm.pick.repository.NoticeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Service
@Transactional(readOnly = true)
class NoticeService(
    private val noticeRepository: NoticeRepository,
) {

    fun findNotice() =
        NoticeResponse(
            clubNotice = noticeRepository.findByCategoryAndDateAfter(
                Category.CLUB,
                Timestamp(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7))
            ).map { it.content },
            memberNotice = noticeRepository.findByCategoryAndDateAfter(
                Category.MEMBER,
                Timestamp(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7))
            ).map { it.content },
        )
}