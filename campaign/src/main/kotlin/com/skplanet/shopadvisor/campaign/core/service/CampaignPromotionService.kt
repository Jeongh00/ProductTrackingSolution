package com.skplanet.shopadvisor.campaign.core.service

import com.skplanet.shopadvisor.campaign.core.mapper.CampaignMapper
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaCreate
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaRemove
import com.skplanet.shopadvisor.campaign.core.repository.CampaignRepository
import com.skplanet.shopadvisor.datasource.RoutingDataSource
import com.skplanet.shopadvisor.datasource.advisor.DataSourceAdvice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class CampaignPromotionService(
    private val campaignMapper: CampaignMapper,
    private val campaignRepository: CampaignRepository
) {

    @Transactional
    fun addPromotionMedia(promotionMediaCreate: PromotionMediaCreate): Long {
        return CampaignServiceHelper.getCampaign(campaignRepository, promotionMediaCreate.campaignId).addMedia(campaignMapper.toPromotionMedia(promotionMediaCreate))
    }

    @Transactional
    fun removePromotionMedia(promotionMediaRemove: PromotionMediaRemove): Long {
        return CampaignServiceHelper.getCampaign(campaignRepository, promotionMediaRemove.campaignId).removeMedia(promotionMediaRemove.mediaId)
    }

    @DataSourceAdvice(route = RoutingDataSource.Route.SERVICE, readOnly = true)
    fun inquiryPromotionMediaList(id: Long): List<PromotionMediaQuery> {
        return campaignMapper.toPromotionListQuery(campaignRepository.findPromotionFetchById(id).orElseThrow { throw EntityNotFoundException("Campaign을 찾을 수 없습니다.") }.campaignPromotionMedia)
    }
}
