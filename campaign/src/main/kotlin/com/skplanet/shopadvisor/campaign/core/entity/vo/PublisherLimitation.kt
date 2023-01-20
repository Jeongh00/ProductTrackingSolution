package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.PublisherLimitationDto
import com.skplanet.shopadvisor.enumeration.ApprovalTypeCode
import com.skplanet.shopadvisor.enumeration.UserTypeCode
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class PublisherLimitation(
    @Column(name = "PUBL_APRVL_TYP_CD")
    var approvalType: ApprovalTypeCode? = null,
    @Column(name = "PUBL_TYP_CD")
    var publisherType: UserTypeCode? = null,
) {

    fun changePublisherLimitation(publisherLimitation: PublisherLimitationDto) {
        publisherLimitation.publisherType?.let { this.publisherType = it }
        publisherLimitation.approvalType?.let { this.approvalType = it }
    }
}
