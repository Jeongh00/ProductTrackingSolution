package com.skplanet.shopadvisor.campaign.participant.mapper.dto

import com.skplanet.shopadvisor.enumeration.ApprovalStatusCode
import com.skplanet.shopadvisor.enumeration.UserTypeCode

data class CampaignParticipationSearch(
    val approvalStatusCode: ApprovalStatusCode? = null,
    val publisherType: UserTypeCode? = null,
    val type: Pair<CampaignParticipationSearchType, String>? = null
)
