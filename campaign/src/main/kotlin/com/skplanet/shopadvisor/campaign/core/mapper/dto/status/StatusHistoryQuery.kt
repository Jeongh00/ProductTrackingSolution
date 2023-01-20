package com.skplanet.shopadvisor.campaign.core.mapper.dto.status

import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import java.time.LocalDateTime

data class StatusHistoryQuery(
    var eventDate: LocalDateTime,
    var status: CampaignStatusCode,
    var managerId: Long,
    var note: String? = null
)
