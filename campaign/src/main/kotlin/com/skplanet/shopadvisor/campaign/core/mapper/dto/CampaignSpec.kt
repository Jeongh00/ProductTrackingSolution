package com.skplanet.shopadvisor.campaign.core.mapper.dto

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignDetailDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignPeriodDataDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignSettingDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignTypeDto
import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.PublisherLimitationDto

data class CampaignSpec(
    var id: Long? = null,
    var campaignDetail: CampaignDetailDto? = null,
    var publisherLimitation: PublisherLimitationDto? = null,
    var campaignPeriodData: CampaignPeriodDataDto? = null,
    var campaignType: CampaignTypeDto? = null,
    var campaignSetting: CampaignSettingDto? = null,
)
