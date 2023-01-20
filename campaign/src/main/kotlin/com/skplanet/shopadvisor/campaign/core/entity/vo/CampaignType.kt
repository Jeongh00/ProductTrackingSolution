package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignTypeDto
import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import com.skplanet.shopadvisor.enumeration.CampaignTypeCode
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class CampaignType(
    @Column(name = "CMPGN_STS_CD")
    @Enumerated(EnumType.STRING)
    var status: CampaignStatusCode? = null,
    @Column(name = " CMPGN_TYP_CD")
    @Enumerated(EnumType.STRING)
    var type: CampaignTypeCode? = null,
) {

    fun changeType(campaignType: CampaignTypeDto) {
        campaignType.type?.let { this.type = it }
    }

    fun changeStatus(status: CampaignStatusCode) {
        this.status = status
    }
}
