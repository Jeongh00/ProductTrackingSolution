package com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded

import com.skplanet.shopadvisor.enumeration.ApprovalTypeCode
import com.skplanet.shopadvisor.enumeration.UserTypeCode

data class PublisherLimitationDto(
    var approvalType: ApprovalTypeCode? = null,
    var publisherType: UserTypeCode? = null,
)
