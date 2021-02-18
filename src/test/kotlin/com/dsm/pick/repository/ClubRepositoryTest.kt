package com.dsm.pick.repository

import com.dsm.pick.domain.attribute.Floor
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ClubRepositoryTest(
    private val clubRepository: ClubRepository
) {

    @Test
    fun `층으로 동아리 조회 OK`() {
        assertThat(clubRepository.findByLocationFloor(Floor.THREE)).map<String> { it.name }.isEqualTo(listOf("테스트동아리"))
    }

    @Test
    fun `없는 층으로 동아리 조회 OK`() {
        assertThat(clubRepository.findByLocationFloor(Floor.TWO)).isEmpty()
    }

    @Test
    fun `층과 우선순위로 동아리 조회 OK`() {
        assertThat(clubRepository.findByLocationFloorAndLocationPriority(Floor.THREE, 0)?.name).isEqualTo("테스트동아리")
    }

    @Test
    fun `없는 층과 우선순위로 동아리 조회 OK`() {
        assertThat(clubRepository.findByLocationFloorAndLocationPriority(Floor.TWO, 10)).isNull()
    }
}