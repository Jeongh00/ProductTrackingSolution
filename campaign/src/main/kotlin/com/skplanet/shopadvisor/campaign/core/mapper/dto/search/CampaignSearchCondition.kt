package com.skplanet.shopadvisor.campaign.core.mapper.dto.search

import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import com.skplanet.shopadvisor.enumeration.CampaignTypeCode

/**
 * @property platformCode
 * @see com.skplanet.shopadvisor.enumeration.PlatformCode
 */
data class CampaignSearchCondition(
    val state: CampaignStatusCode? = null,
    val typeCode: CampaignTypeCode? = null,
    val platformCode: Int? = null,
    val showYN: String? = null,
    val type: Pair<CampaignSearchType, String>? = null
)
