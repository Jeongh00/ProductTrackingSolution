package com.skplanet.shopadvisor.campaign.core.mapper.dto

import com.querydsl.core.annotations.QueryProjection

data class CampaignDetailQuery
@QueryProjection
constructor(
    var name: String,
    var advertiserId: Long
)
