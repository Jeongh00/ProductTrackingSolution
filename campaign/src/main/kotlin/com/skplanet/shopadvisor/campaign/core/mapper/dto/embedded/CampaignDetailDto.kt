package com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded

import java.time.LocalDateTime

data class CampaignDetailDto(
    var name: String? = null,
    var advertiserId: Long? = null,
    var url: String? = null,
    var site: SiteDto? = null,
    var description: String? = null,
    var caution: String? = null,
    var lastSettlementDateTime: LocalDateTime? = null,
)
