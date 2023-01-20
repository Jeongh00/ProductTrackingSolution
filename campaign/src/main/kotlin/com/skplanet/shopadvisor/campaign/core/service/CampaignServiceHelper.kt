package com.skplanet.shopadvisor.campaign.core.service

import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.campaign.core.repository.CampaignRepository
import javax.persistence.EntityNotFoundException

object CampaignServiceHelper {

    internal fun getCampaign(campaignRepository: CampaignRepository, id: Long): Campaign {
        return campaignRepository.findById(id).orElseThrow { throw EntityNotFoundException("Campaign을 찾을 수 없습니다.") }
    }
}
