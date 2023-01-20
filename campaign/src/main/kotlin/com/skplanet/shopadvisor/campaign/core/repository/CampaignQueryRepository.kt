package com.skplanet.shopadvisor.campaign.core.repository

import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.search.CampaignSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CampaignQueryRepository {

    fun findSearchByCondition(campaignSearchCondition: CampaignSearchCondition, pageable: Pageable): Page<CampaignQuery>
}
