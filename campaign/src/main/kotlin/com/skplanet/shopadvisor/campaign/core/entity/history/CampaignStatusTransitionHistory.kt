package com.skplanet.shopadvisor.campaign.core.entity.history

import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.common.entity.CreatedOnlyBaseEntity
import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "campaign_status_hist")
class CampaignStatusTransitionHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "MGR_ID")
    val managerId: Long? = null,
    @Column(name = "CMPGN_STT_CD")
    @Enumerated(EnumType.STRING)
    val status: CampaignStatusCode? = null,
    @Column(name = "REMARK")
    val note: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMPGN_ID")
    var campaign: Campaign? = null
) : CreatedOnlyBaseEntity()
