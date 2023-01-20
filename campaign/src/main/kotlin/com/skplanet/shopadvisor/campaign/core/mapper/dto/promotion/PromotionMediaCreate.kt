package com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion

import com.skplanet.shopadvisor.enumeration.PromotionMediaTypeCode

data class PromotionMediaCreate(
    var campaignId: Long,
    var url: String,
    var originFileName: String,
    var type: PromotionMediaTypeCode
)
