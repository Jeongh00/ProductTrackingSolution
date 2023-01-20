package com.skplanet.shopadvisor.campaign.core.mapper.dto.promotion

import com.skplanet.shopadvisor.enumeration.PromotionMediaTypeCode

data class PromotionMediaQuery(
    var id: Long? = null,
    var originalFileName: String? = null,
    var url: String? = null,
    var type: PromotionMediaTypeCode? = null
)
