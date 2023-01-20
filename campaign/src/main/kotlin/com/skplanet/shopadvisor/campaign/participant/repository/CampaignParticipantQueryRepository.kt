package com.skplanet.shopadvisor.campaign.participant.repository

import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationQuery
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationSearch
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CampaignParticipantQueryRepository {

    fun findSearchByCondition(campaignParticipationSearch: CampaignParticipationSearch, pageable: Pageable): Page<CampaignParticipationQuery>
}
