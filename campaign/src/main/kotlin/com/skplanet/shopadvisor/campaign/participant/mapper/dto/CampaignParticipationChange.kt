package com.skplanet.shopadvisor.campaign.participant.mapper.dto

import com.skplanet.shopadvisor.enumeration.ApprovalStatusCode

data class CampaignParticipationChange(
    val participantId: Long,
    val approvalStatusCode: ApprovalStatusCode,
    val denyContent: String? = null
)
