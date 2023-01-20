package com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded

import com.skplanet.shopadvisor.campaign.core.entity.vo.Schedule
import java.time.LocalDateTime

data class ScheduleDto(
    var startAt: LocalDateTime? = null,
    var endAt: LocalDateTime? = null,
    var isInfinite: Boolean? = null,
) {

    fun toSchedule(): Schedule {
        return Schedule(startAt, endAt, isInfinite)
    }
}
