package com.skplanet.shopadvisor.campaign.core.service

import com.skplanet.shopadvisor.campaign.CampaignAutoConfiguration
import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.campaign.core.entity.media.CampaignPromotionMedia
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignSpec
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignDetailDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignSettingDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignTypeDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaCreate
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaRemove
import com.skplanet.shopadvisor.campaign.core.mapper.dto.search.CampaignSearchCondition
import com.skplanet.shopadvisor.campaign.core.mapper.dto.status.CampaignChangeStatus
import com.skplanet.shopadvisor.config.QuerydslConfig
import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import com.skplanet.shopadvisor.enumeration.CampaignTypeCode
import com.skplanet.shopadvisor.enumeration.PlatformCode
import com.skplanet.shopadvisor.enumeration.PromotionMediaTypeCode
import com.skplanet.shopadvisor.member.ShopAdvisorMemberAutoConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ContextConfiguration
import javax.persistence.EntityManager

@DataJpaTest
@ContextConfiguration(classes = [CampaignAutoConfiguration::class, ShopAdvisorMemberAutoConfiguration::class, QuerydslConfig::class])
class CampaignServiceTest
@Autowired
constructor(
    private val campaignService: CampaignService,
    private val campaignPromotionService: CampaignPromotionService,
    private val campaignStatusHistoryService: CampaignStatusHistoryService,
    private val entityManager: EntityManager
) {

    @Test
    @DisplayName("캠페인 생성 테스트")
    fun sut_create_campaign_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"))

        // when
        val createCampaign = campaignService.createCampaign(dto)

        // then
        assertTrue(createCampaign > 0)
    }

    @Test
    @DisplayName("캠페인 변경 테스트")
    fun sut_modify_campaign_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"))
        val createId = campaignService.createCampaign(dto)
        val updateDto = CampaignSpec(id = createId, campaignDetail = CampaignDetailDto(name = "TEST3"))

        // when
        entityManager.flush()
        entityManager.clear()
        val id = campaignService.modifyCampaign(updateDto)

        val entity = entityManager.find(Campaign::class.java, id)

        // then
        assertEquals("TEST3", entity.campaignDetail!!.name)
    }

    @Test
    @DisplayName("프로모션 조회 테스트")
    fun sut_inquiry_promotion_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"))
        val createId = campaignService.createCampaign(dto)
        val entity = entityManager.find(Campaign::class.java, createId)

        // when

        entity.addMedia(CampaignPromotionMedia(originalFileName = "FILE1", type = PromotionMediaTypeCode.REPRESENT))
        entity.addMedia(CampaignPromotionMedia(originalFileName = "FILE2", type = PromotionMediaTypeCode.IMAGE_BANNER))
        entityManager.flush()
        entityManager.clear()

        val medias = campaignPromotionService.inquiryPromotionMediaList(entity.id!!)

        // then
        assertEquals(2, medias.size)
        assertEquals("FILE1", medias[0].originalFileName)
        assertEquals("FILE2", medias[1].originalFileName)
        assertEquals(PromotionMediaTypeCode.REPRESENT, medias[0].type)
        assertEquals(PromotionMediaTypeCode.IMAGE_BANNER, medias[1].type)
    }

    @Test
    @DisplayName("프로모션 추가 테스트")
    fun sut_promotion_create_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"))
        val createId = campaignService.createCampaign(dto)

        // when
        campaignPromotionService.addPromotionMedia(PromotionMediaCreate(createId, "URL", "NAME", PromotionMediaTypeCode.REPRESENT))
        campaignPromotionService.addPromotionMedia(PromotionMediaCreate(createId, "URL", "NAME", PromotionMediaTypeCode.REPRESENT))
        campaignPromotionService.addPromotionMedia(PromotionMediaCreate(createId, "URL", "NAME", PromotionMediaTypeCode.REPRESENT))

        val medias = campaignPromotionService.inquiryPromotionMediaList(createId)
        // then
        assertEquals(3, medias.size)
        assertEquals(PromotionMediaTypeCode.REPRESENT, medias[0].type)
    }

    @Test
    @DisplayName("프로모션 삭제 테스트")
    fun sut_promotion_delete_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"))
        val createId = campaignService.createCampaign(dto)

        campaignPromotionService.addPromotionMedia(PromotionMediaCreate(createId, "URL", "NAME", PromotionMediaTypeCode.REPRESENT))
        campaignPromotionService.addPromotionMedia(PromotionMediaCreate(createId, "URL", "NAME", PromotionMediaTypeCode.REPRESENT))
        campaignPromotionService.addPromotionMedia(PromotionMediaCreate(createId, "URL", "NAME", PromotionMediaTypeCode.REPRESENT))

        // when
        val medias = campaignPromotionService.inquiryPromotionMediaList(createId)
        campaignPromotionService.removePromotionMedia(PromotionMediaRemove(createId, medias[0].id!!))

        entityManager.flush()
        entityManager.clear()
        val actualMedias = campaignPromotionService.inquiryPromotionMediaList(createId)

        // then
        assertEquals(3, medias.size)
        assertEquals(2, actualMedias.size)
    }

    @Test
    @DisplayName("캠페인 활성 변경 이력 저장 및 조회 테스트")
    fun sut_status_history_save_and_inquiry_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"), campaignType = CampaignTypeDto(status = CampaignStatusCode.LIVE_OFF))
        val createId = campaignService.createCampaign(dto)

        // when
        campaignStatusHistoryService.changeCampaignStatus(CampaignChangeStatus(createId, 1L, CampaignStatusCode.LIVE_ON, "NOTE"))
        campaignStatusHistoryService.changeCampaignStatus(CampaignChangeStatus(createId, 1L, CampaignStatusCode.END, "NOTE"))

        entityManager.flush()
        entityManager.clear()

        val list = campaignStatusHistoryService.inquiryStatusHistory(createId)
        val entity = campaignService.inquiryDetailedCampaignWithoutImage(createId)

        // then
        assertEquals(list.size, 2)
        assertEquals(CampaignStatusCode.LIVE_OFF, list[0].status)
        assertEquals(CampaignStatusCode.LIVE_ON, list[1].status)
        assertEquals(CampaignStatusCode.END, entity.campaignType!!.status)
    }

    @Test
    @DisplayName("캠페인 이미지 없는 조회 테스트")
    fun sut_inquiry_detailed_campaign_without_image_test() {
        // given
        val dto = CampaignSpec(campaignDetail = CampaignDetailDto(name = "TEST"))
        val createId = campaignService.createCampaign(dto)

        // when
        val entity = campaignService.inquiryDetailedCampaignWithoutImage(createId)

        // then
        assertEquals(dto.campaignDetail!!.name, entity.campaignDetail!!.name)
    }

    @Test
    @DisplayName("캠페인 이미지 없는 페이징 테스트")
    fun sut_inquiry_page_without_image_test() {
        // given
        val dto1 = CampaignSpec(
            campaignDetail = CampaignDetailDto(name = "TEST1"),
            campaignSetting = CampaignSettingDto(platform = PlatformCode.AOS.value),
            campaignType = CampaignTypeDto(CampaignStatusCode.END, CampaignTypeCode.CPA)
        )
        val dto2 = CampaignSpec(
            campaignDetail = CampaignDetailDto(name = "TEST1"),
            campaignSetting = CampaignSettingDto(platform = 3),
            campaignType = CampaignTypeDto(CampaignStatusCode.LIVE_OFF, CampaignTypeCode.CPA)
        )
        val dto3 = CampaignSpec(
            campaignDetail = CampaignDetailDto(name = "TEST1"),
            campaignSetting = CampaignSettingDto(platform = PlatformCode.ALL.value),
            campaignType = CampaignTypeDto(CampaignStatusCode.LIVE_OFF, CampaignTypeCode.CPA)
        )
        campaignService.createCampaign(dto1)
        campaignService.createCampaign(dto2)
        campaignService.createCampaign(dto3)

        val condition = CampaignSearchCondition(state = CampaignStatusCode.LIVE_OFF, typeCode = CampaignTypeCode.CPA, platformCode = PlatformCode.IOS.value)

        // when
        val pages = campaignService.inquiryCampaignPageWithoutImage(condition, PageRequest.of(0, 10))

        // then
        assertEquals(2, pages.totalElements)
    }
}
