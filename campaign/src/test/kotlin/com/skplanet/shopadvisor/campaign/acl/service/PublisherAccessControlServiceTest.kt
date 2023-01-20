package com.skplanet.shopadvisor.campaign.acl.service

import com.skplanet.shopadvisor.campaign.CampaignAutoConfiguration
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlChange
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlRegister
import com.skplanet.shopadvisor.enumeration.RestrictionSetupCode
import com.skplanet.shopadvisor.member.entity.PublisherMember
import com.skplanet.shopadvisor.member.entity.embed.MemberDetail
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import javax.persistence.EntityManager

@DataJpaTest
@EntityScan(basePackages = ["com.skplanet.shopadvisor.**.entity"])
@EnableAutoConfiguration
@ImportAutoConfiguration(CampaignAutoConfiguration::class)
@ContextConfiguration(classes = [PublisherAccessControlService::class])
class PublisherAccessControlServiceTest
@Autowired
constructor(
    private val publisherAccessControlService: PublisherAccessControlService,
    private val entityManager: EntityManager
) {

    @Test
    @DisplayName("매체 블랙리스트 설정 테스트")
    fun sut_acl_blacklist_test() {
        // given
        val pACL = PublisherAccessControlRegister(1L, 1L, RestrictionSetupCode.BLACK)

        // when
        val id = publisherAccessControlService.registerACL(pACL)

        // then
        assertTrue(id > 0)
    }

    @Test
    @DisplayName("매체 블랙리스트에서 화이트리스트로 변경 테스트")
    fun sut_change_to_whitelist_from_blacklist_test() {
        // given
        val pACL = PublisherAccessControlRegister(1L, 1L, RestrictionSetupCode.BLACK)
        val blackId = publisherAccessControlService.registerACL(pACL)

        // when
        val changeACL = publisherAccessControlService.changeACL(PublisherAccessControlChange(blackId, RestrictionSetupCode.WHITE))
        entityManager.flush()

        // then
        assertEquals(blackId, changeACL)
    }

    @Test
    @DisplayName("ACL 삭제 및 캠페인 조회 테스트")
    fun sut_acl_delete() {
        // given
        val pACL = PublisherAccessControlRegister(1L, 1L, RestrictionSetupCode.BLACK)
        val blackId = publisherAccessControlService.registerACL(pACL)

        entityManager.persist(PublisherMember(detail = MemberDetail(name = "JJH")))

        // when
        val beforeSize = publisherAccessControlService.getACLs(1L).size
        publisherAccessControlService.deleteACL(blackId)
        entityManager.flush()
        val afterSize = publisherAccessControlService.getACLs(1L).size

        // then
        assertEquals(1, beforeSize)
        assertEquals(0, afterSize)
    }
}
