package com.skplanet.shopadvisor.campaign.core.mapper.dto.status

import com.skplanet.shopadvisor.enumeration.CampaignStatusCode

data class CampaignChangeStatus(
    var id: Long,
    var managerId: Long,
    var campaignStatusCode: CampaignStatusCode,
    var note: String
)
