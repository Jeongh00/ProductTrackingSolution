package com.skplanet.shopadvisor.campaign.program.mapper.dto

import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionConfigurationDto
import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionPeriodDto
import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.ProgramDetailDto

data class ProgramSpec(
    var id: Long? = null,
    var campaignId: Long? = null,
    var showYN: String? = null,
    var programCodeInfo: ProgramCodeInfo? = null,
    var commissionConfig: CommissionConfigurationDto? = null,
    var commissionPeriod: CommissionPeriodDto? = null,
    var programDetail: ProgramDetailDto? = null,
    var priority: Int? = null,
)
