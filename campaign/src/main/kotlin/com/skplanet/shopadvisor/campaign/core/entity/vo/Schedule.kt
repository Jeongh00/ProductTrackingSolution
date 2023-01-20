package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.ScheduleDto
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Schedule(
    @Column(name = "START_DTTM")
    var startAt: LocalDateTime? = null,
    @Column(name = "END_DTTM")
    var endAt: LocalDateTime? = null,
    @Column(name = "WTOUT_DELN_YN")
    var isInfinite: Boolean? = null,
) {

    fun changeSchedule(schedule: ScheduleDto) {
        schedule.startAt?.let { this.startAt = it }
        schedule.endAt?.let { this.endAt = it }
        schedule.isInfinite?.let { this.isInfinite = it }
    }
}
