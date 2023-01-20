package com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded

import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import com.skplanet.shopadvisor.enumeration.CampaignTypeCode

data class CampaignTypeDto(
    var status: CampaignStatusCode? = null,
    var type: CampaignTypeCode? = null,
)
