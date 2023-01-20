package com.skplanet.shopadvisor.campaign.acl.mapper.dto

import com.skplanet.shopadvisor.enumeration.RestrictionSetupCode

data class PublisherAccessControlChange(
    val accessControlId: Long,
    val restrictionSetupCode: RestrictionSetupCode
)
