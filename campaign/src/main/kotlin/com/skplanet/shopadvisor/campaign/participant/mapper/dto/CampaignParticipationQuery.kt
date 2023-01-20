package com.skplanet.shopadvisor.campaign.participant.mapper.dto

import com.querydsl.core.annotations.QueryProjection
import com.skplanet.shopadvisor.enumeration.ApprovalStatusCode
import com.skplanet.shopadvisor.enumeration.UserTypeCode
import java.time.LocalDateTime

data class CampaignParticipationQuery
@QueryProjection
constructor(
    val campaignId: Long,
    val applyDateTime: LocalDateTime,
    val changeDateTime: LocalDateTime,
    val publisherType: UserTypeCode,
    val publisherLoginId: String,
    val publisherName: String,
    val campaignName: String,
    val approvalStatusCode: ApprovalStatusCode
)
