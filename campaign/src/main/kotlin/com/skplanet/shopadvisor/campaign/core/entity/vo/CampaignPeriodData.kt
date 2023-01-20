package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignPeriodDataDto
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * 캠페인 정산 정보
 *
 * @param publisherSettlementPeriod 매체 정산 시기는 캠페인의 마지막 정산 일정과 비교하여 연산 월 단위만 표현 2는 2개월 4는 4개월
 * @param admitRecordPeriod 실적 인정 기간은 시간단위로만 표현 24는 24시간, 12는 12시간
 */
@Embeddable
data class CampaignPeriodData(
    @Column(name = "PUBL_STLMT_PERD")
    var publisherSettlementPeriod: String? = null,
    @Column(name = "ERNG_APPR_PERD")
    var admitRecordPeriod: String? = null
) {

    fun changePeriodData(campaignPeriodData: CampaignPeriodDataDto) {
        campaignPeriodData.admitRecordPeriod?.let { this.admitRecordPeriod = it }
        campaignPeriodData.publisherSettlementPeriod?.let { this.publisherSettlementPeriod = it }
    }
}
