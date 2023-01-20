package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignDetailDto
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class CampaignDetail(
    @Column(name = "CMPGN_NM")
    var name: String? = null,
    @Column(name = "ADVE_ID")
    var advertiserId: Long? = null,
    @Column(name = "URL")
    var url: String? = null,
    @Embedded
    var site: Site? = null,
    @Column(name = "CMPGN_DESC")
    var description: String? = null,
    @Column(name = "CAUTION")
    var caution: String? = null,
    var lastSettlementDateTime: LocalDateTime? = null,
) {

    fun changeDetail(campaignDetail: CampaignDetailDto) {
        campaignDetail.advertiserId?.let { this.advertiserId = it }
        campaignDetail.name?.let { this.name = it }
        campaignDetail.url?.let { this.url = it }
        campaignDetail.site?.let { this.site = it.toSite() }
        campaignDetail.description?.let { this.description = it }
        campaignDetail.caution?.let { this.caution = it }
    }

    fun completeSettlement() {
        this.lastSettlementDateTime = LocalDateTime.now()
    }
}
