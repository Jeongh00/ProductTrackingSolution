package com.skplanet.shopadvisor.campaign.program.mapper.dto

import com.skplanet.shopadvisor.enumeration.PlatformCode
import java.time.LocalDateTime

data class ProgramPickup(
    val campaignId: Long,
    val platformCode: PlatformCode,
    val categoryCodeList: List<String>,
    val productDateTime: LocalDateTime
)
