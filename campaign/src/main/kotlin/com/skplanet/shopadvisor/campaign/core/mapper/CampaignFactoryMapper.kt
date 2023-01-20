package com.skplanet.shopadvisor.campaign.core.mapper

import com.skplanet.shopadvisor.campaign.core.entity.history.CampaignStatusTransitionHistory
import com.skplanet.shopadvisor.campaign.core.entity.media.CampaignPromotionMedia
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignDetail
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignSetting
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignDetailQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignSettingQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion.PromotionMediaQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.status.StatusHistoryQuery
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CampaignFactoryMapper {

    @Mapping(target = "eventDate", source = "createdAt")
    fun toStatusHistoryQuery(campaignStatusTransitionHistory: CampaignStatusTransitionHistory): StatusHistoryQuery

    fun toPromotionQuery(campaignPromotionMedia: CampaignPromotionMedia): PromotionMediaQuery

    fun toCampaignSettingQuery(campaignSetting: CampaignSetting): CampaignSettingQuery

    fun toCampaignDetailQuery(campaignDetail: CampaignDetail): CampaignDetailQuery
}
