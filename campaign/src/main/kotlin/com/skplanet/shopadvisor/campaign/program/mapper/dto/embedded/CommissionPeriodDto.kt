package com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded

import java.time.LocalDateTime

data class CommissionPeriodDto(
    var startAt: LocalDateTime? = null,
    var endAt: LocalDateTime? = null,
    var isInfiniteYN: String? = null,
)
