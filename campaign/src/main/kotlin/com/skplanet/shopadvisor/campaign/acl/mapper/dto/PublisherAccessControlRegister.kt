package com.skplanet.shopadvisor.campaign.acl.mapper.dto

import com.skplanet.shopadvisor.enumeration.RestrictionSetupCode

data class PublisherAccessControlRegister(
    val campaignId: Long,
    val publisherId: Long,
    val restrictionSetupCode: RestrictionSetupCode,
)
