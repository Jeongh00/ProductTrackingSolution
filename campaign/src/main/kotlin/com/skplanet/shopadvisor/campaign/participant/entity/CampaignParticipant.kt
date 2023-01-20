package com.skplanet.shopadvisor.campaign.participant.entity

import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationChange
import com.skplanet.shopadvisor.common.entity.BaseEntity
import com.skplanet.shopadvisor.enumeration.ApprovalStatusCode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "campaign_part_publ_list")
class CampaignParticipant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "CMPGN_ID")
    var campaignId: Long,
    @Column(name = "PUBL_ID")
    var publisherId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "ACS_RSTRCTN_SETUP_CD")
    var approvalStatusCode: ApprovalStatusCode?,
    @Column(name = "DENY_CONTENT")
    var denyContent: String? = null
) : BaseEntity() {

    @PrePersist
    private fun prePersist() {
        approvalStatusCode = ApprovalStatusCode.UNDER_REVIEW
    }

    fun updateStatus(campaignParticipationChange: CampaignParticipationChange) = when (campaignParticipationChange.approvalStatusCode) {
        ApprovalStatusCode.UNDER_REVIEW ->
            {
                throw IllegalStateException("대기상태는 유효하지 않은 상태값입니다.")
            }

        ApprovalStatusCode.DENY ->
            {
                approvalStatusCode = campaignParticipationChange.approvalStatusCode
                denyContent = campaignParticipationChange.denyContent
            }

        ApprovalStatusCode.APPROVE ->
            {
                approvalStatusCode = campaignParticipationChange.approvalStatusCode
            }
    }
}
