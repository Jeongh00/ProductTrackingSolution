package com.skplanet.shopadvisor.campaign.core.mapper.dto

import com.querydsl.core.annotations.QueryProjection
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignType
import java.time.LocalDateTime

data class CampaignQuery
@QueryProjection
constructor(
    var id: Long? = null,
    var campaignType: CampaignType? = null,
    var campaignDetailQuery: CampaignDetailQuery? = null,
    var campaignSettingQuery: CampaignSettingQuery? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)
