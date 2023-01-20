package com.skplanet.shopadvisor.campaign.participant.service

import com.skplanet.shopadvisor.campaign.CampaignAutoConfiguration
import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignDetail
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationApply
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationChange
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationSearch
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationSearchType
import com.skplanet.shopadvisor.enumeration.ApprovalStatusCode
import com.skplanet.shopadvisor.enumeration.UserTypeCode
import com.skplanet.shopadvisor.member.entity.PublisherMember
import com.skplanet.shopadvisor.member.entity.embed.MemberDetail
import com.skplanet.shopadvisor.member.entity.embed.MemberTypeInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.ContextConfiguration
import javax.persistence.EntityManager

@DataJpaTest
@EntityScan("com.skplanet.shopadvisor.**.entity")
@EnableAutoConfiguration
@ImportAutoConfiguration(CampaignAutoConfiguration::class)
@ContextConfiguration(classes = [CampaignParticipantService::class])
@EnableJpaAuditing
class CampaignParticipantServiceTest
@Autowired
constructor(
    private val campaignParticipantService: CampaignParticipantService,
    private val entityManager: EntityManager
) {

    @Test
    @DisplayName("캠페인 참여 신청 테스트")
    fun sut_campaign_participant_save_test() {
        // given
        val campaignParticipationApply = CampaignParticipationApply(1L, 1L)

        // when
        val id = this.campaignParticipantService.applyParticipation(campaignParticipationApply)

        // then
        assertTrue(id > 0)
    }

    @Test
    @DisplayName("캠페인 참여 및 승인 절차 테스트")
    fun sut_campaign_apply_and_approve_test() {
        // given
        val campaignParticipationApply = CampaignParticipationApply(1L, 1L)
        val applyId = this.campaignParticipantService.applyParticipation(campaignParticipationApply)
        val campaignParticipationChange = CampaignParticipationChange(applyId, ApprovalStatusCode.APPROVE)

        // when
        val approveId = campaignParticipantService.changeParticipation(campaignParticipationChange)

        // then
        assertEquals(applyId, approveId)
    }

    @Test
    @DisplayName("캠페인 참여 승인창 쿼리 테스트")
    fun sut_campaign_participation_apply_test() {
        // given
        val publisherMember = PublisherMember(detail = MemberDetail(loginId = "IDID", name = "JJH"))
        val campaign = Campaign(campaignDetail = CampaignDetail(name = "TESTEST"))
        entityManager.persist(publisherMember)
        entityManager.persist(campaign)

        entityManager.flush()

        val campaignParticipationApply = CampaignParticipationApply(campaign.id!!, publisherMember.id!!)
        val applyId = this.campaignParticipantService.applyParticipation(campaignParticipationApply)

        // when
        val (publisherLoginId, publisherName, campaignName) = campaignParticipantService.showChangeQuery(applyId)

        // then
        assertEquals("IDID", publisherLoginId)
        assertEquals("JJH", publisherName)
        assertEquals("TESTEST", campaignName)
    }

    @Test
    @DisplayName("캠페인 참여 신청 목록 검색 테스트")
    fun sut_campaign_participation_search_test() {
        // given
        val publisherMember = PublisherMember(
            detail = MemberDetail(loginId = "ID", name = "JJH"),
            memberTypeInfo = MemberTypeInfo(userTypeCode = UserTypeCode.PERSONAL)
        )
        val campaign = Campaign(campaignDetail = CampaignDetail(name = "TEST"))
        entityManager.persist(publisherMember)
        entityManager.persist(campaign)

        val campaignParticipationApply = CampaignParticipationApply(campaign.id!!, publisherMember.id!!)
        this.campaignParticipantService.applyParticipation(campaignParticipationApply)

        // when
        val searchResult = campaignParticipantService.searchParticipants(
            CampaignParticipationSearch(
                ApprovalStatusCode.UNDER_REVIEW,
                UserTypeCode.PERSONAL,
                Pair(CampaignParticipationSearchType.CAMPAIGN_NAME, "TEST")
            ),
            PageRequest.of(0, 10)
        )

        val firstElement = searchResult.get().findFirst().get()

        // then
        assertEquals(1, searchResult.totalElements)
        assertEquals(1, searchResult.totalPages)
        assertEquals(1, firstElement.campaignId)
        assertEquals("TEST", firstElement.campaignName)
        assertEquals("ID", firstElement.publisherLoginId)
        assertEquals("JJH", firstElement.publisherName)
    }
}
