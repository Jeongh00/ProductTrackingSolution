package com.skplanet.shopadvisor.campaign.participant.mapper.dto

data class CampaignParticipationChangeQuery(
    val publisherLoginId: String,
    val publisherName: String,
    val campaignName: String
)
