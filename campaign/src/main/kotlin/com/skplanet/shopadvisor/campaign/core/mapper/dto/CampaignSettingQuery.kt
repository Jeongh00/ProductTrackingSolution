package com.skplanet.shopadvisor.campaign.core.mapper.dto

import com.querydsl.core.annotations.QueryProjection

data class CampaignSettingQuery
@QueryProjection
constructor(
    var platform: Int? = null,
    var showYN: String? = null
)
