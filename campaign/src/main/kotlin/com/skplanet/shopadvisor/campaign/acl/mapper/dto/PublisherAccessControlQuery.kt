package com.skplanet.shopadvisor.campaign.acl.mapper.dto

import com.skplanet.shopadvisor.enumeration.RestrictionSetupCode

data class PublisherAccessControlQuery(
    val publisherName: String,
    val restrictionSetupCode: RestrictionSetupCode
)
