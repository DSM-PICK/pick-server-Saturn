package com.dsm.pick.repository

import com.dsm.pick.domain.attribute.Category
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import java.sql.Timestamp

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class NoticeRepositoryTest(
    private val noticeRepository: NoticeRepository
) {

    @Test
    fun `생성시간으로 멤버 공지사항 조회 OK`() {
        val notices =
            noticeRepository.findByCategoryAndDateAfter(Category.MEMBER, Timestamp(System.currentTimeMillis() - (1000 * 60)))
                .singleOrNull()

        assertThat(notices).isNotNull
        assertThat(notices?.id).isEqualTo(1)
        assertThat(notices?.category).isEqualTo(Category.MEMBER)
        assertThat(notices?.content).isEqualTo("멤버공지")
    }

    @Test
    fun `생성시간으로 동아리 공지사항 조회 OK`() {
        val notices =
            noticeRepository.findByCategoryAndDateAfter(Category.CLUB, Timestamp(System.currentTimeMillis() - (1000 * 60)))
                .singleOrNull()

        assertThat(notices).isNotNull
        assertThat(notices?.id).isEqualTo(2)
        assertThat(notices?.category).isEqualTo(Category.CLUB)
        assertThat(notices?.content).isEqualTo("동아리공지")
    }

    @Test
    fun `없는 생성시간으로 공지사항 조회 OK`() {
        assertThat(
            noticeRepository.findByCategoryAndDateAfter(Category.MEMBER, Timestamp(System.currentTimeMillis() + (1000 * 60)))
        ).isEmpty()
        assertThat(
            noticeRepository.findByCategoryAndDateAfter(Category.CLUB, Timestamp(System.currentTimeMillis() + (1000 * 60)))
        ).isEmpty()
    }
}