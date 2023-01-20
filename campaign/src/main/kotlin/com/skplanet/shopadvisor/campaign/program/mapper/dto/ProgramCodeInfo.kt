package com.skplanet.shopadvisor.campaign.program.mapper.dto

import com.skplanet.shopadvisor.enumeration.CommissionApplyTypeCode
import com.skplanet.shopadvisor.enumeration.PlatformCode

data class ProgramCodeInfo(
    val applyType: CommissionApplyTypeCode?,
    val platformCode: List<PlatformCode>?,
    val categoryCode: List<String>?,
)
