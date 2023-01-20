package com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded

data class CampaignSettingDto(
    var schedule: ScheduleDto? = null,
    var categoryName: String? = null,
    var platform: Int? = null,
    var deepLinkYn: String? = null,
    var showYN: String? = null,
)
