package com.skplanet.shopadvisor.campaign.program.entity

import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionPeriodDto
import com.skplanet.shopadvisor.utils.between
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class CommissionPeriod(
    @Column(name = "START_DTTM")
    var startAt: LocalDateTime? = null,
    @Column(name = "END_DTTM")
    var endAt: LocalDateTime? = null,
    @Column(name = "WTOUT_DELN_YN")
    var isInfiniteYN: String? = null,
) {

    fun changeCommissionPeriod(commissionPeriod: CommissionPeriodDto) {
        commissionPeriod.startAt?.let { this.startAt = it }
        commissionPeriod.endAt?.let { this.endAt = it }
        commissionPeriod.isInfiniteYN?.let { this.isInfiniteYN = it }
    }

    fun isInclude(date: LocalDateTime): Boolean {
        return isInfiniteYN == "Y" || date.between(startAt!!, endAt!!)
    }
}
