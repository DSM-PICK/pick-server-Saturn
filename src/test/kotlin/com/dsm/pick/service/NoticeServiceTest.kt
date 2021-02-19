package com.dsm.pick.service

import com.dsm.pick.domain.Admin
import com.dsm.pick.domain.Notice
import com.dsm.pick.domain.attribute.Category
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.sql.Timestamp

internal class NoticeServiceTest {

    @Test
    fun `최근 1주간의 공지사항 찾기 OK`() {
        val noticeResponse = noticeService.findNotice()

        assertThat(noticeResponse.clubNotice).containsAll(listOf("clubNotice"))
        assertThat(noticeResponse.memberNotice).containsAll(listOf("memberNotice"))
    }

    private val noticeService = NoticeService(
        noticeRepository = mock {
            on { findByCategoryAndDateAfter(eq(Category.CLUB), any()) } doReturn listOf(
                Notice(
                    id = 1,
                    content = "clubNotice",
                    admin = Admin("admin", "admin"),
                    category = Category.CLUB,
                    date = Timestamp(1)
                )
            )
            on { findByCategoryAndDateAfter(eq(Category.MEMBER), any()) } doReturn listOf(
                Notice(
                    id = 2,
                    content = "memberNotice",
                    admin = Admin("admin", "admin"),
                    category = Category.MEMBER,
                    date = Timestamp(1)
                )
            )
        }
    )
}