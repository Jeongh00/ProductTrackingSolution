package com.skplanet.shopadvisor.campaign.program.mapper.dto

import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionConfigurationDto
import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionPeriodDto

data class ProgramQuery(
    val id: Long,
    val priority: Int,
    val programCodeInfo: ProgramCodeInfo?,
    val commissionPeriod: CommissionPeriodDto?,
    val commissionConfig: CommissionConfigurationDto?,
    val showYN: String,
)
