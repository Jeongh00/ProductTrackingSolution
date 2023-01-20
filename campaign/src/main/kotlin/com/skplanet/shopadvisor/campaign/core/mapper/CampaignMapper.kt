package com.skplanet.shopadvisor.campaign.core.mapper

import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.campaign.core.entity.history.CampaignStatusTransitionHistory
import com.skplanet.shopadvisor.campaign.core.entity.media.CampaignPromotionMedia
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignSpec
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaCreate
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.status.StatusHistoryQuery
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [CampaignFactoryMapper::class], unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CampaignMapper {

    @Mapping(target = "id", ignore = true)
    fun toCampaign(campaignSpec: CampaignSpec): Campaign

    fun toCampaignDto(campaign: Campaign): CampaignSpec

    fun toStatusHistoryListQuery(statusTransitionHistory: List<CampaignStatusTransitionHistory>): List<StatusHistoryQuery>

    fun toPromotionListQuery(promotionList: List<CampaignPromotionMedia>): List<PromotionMediaQuery>

    @Mapping(target = "campaignDetailQuery", source = "campaignDetail")
    @Mapping(target = "campaignSettingQuery", source = "campaignSetting")
    fun toCampaignQuery(campaign: Campaign): CampaignQuery

    fun toPromotionMedia(promotionMediaCreate: PromotionMediaCreate): CampaignPromotionMedia
}
