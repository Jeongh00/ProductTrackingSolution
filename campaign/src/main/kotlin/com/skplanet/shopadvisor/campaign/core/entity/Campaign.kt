package com.skplanet.shopadvisor.campaign.core.entity

import com.skplanet.shopadvisor.campaign.core.entity.history.CampaignStatusTransitionHistory
import com.skplanet.shopadvisor.campaign.core.entity.media.CampaignPromotionMedia
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignDetail
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignPeriodData
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignSetting
import com.skplanet.shopadvisor.campaign.core.entity.vo.CampaignType
import com.skplanet.shopadvisor.campaign.core.entity.vo.PublisherLimitation
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignSpec
import com.skplanet.shopadvisor.campaign.core.mapper.dto.status.CampaignChangeStatus
import com.skplanet.shopadvisor.common.entity.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "campaign")
class Campaign(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Embedded
    var campaignDetail: CampaignDetail? = null,
    @Embedded
    var publisherLimitation: PublisherLimitation? = null,
    @Embedded
    var campaignPeriodData: CampaignPeriodData? = null,
    @Embedded
    var campaignSetting: CampaignSetting? = null,
    @Embedded
    var campaignType: CampaignType? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH])
    val campaignStatusTransitionHistory: MutableList<CampaignStatusTransitionHistory> = ArrayList(),
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH])
    val campaignPromotionMedia: MutableList<CampaignPromotionMedia> = ArrayList(),
    // TODO : 작성자 수정자 반영이 필요함 추 후 할 예정
    @Column(name = "CRT_BY_ID")
    var createdBy: Long? = null,
    @Column(name = "UPDR_ID")
    var updatedBy: Long? = null
) : BaseEntity() {

    fun addMedia(media: CampaignPromotionMedia): Long {
        media.campaign = this
        campaignPromotionMedia.add(media)

        return id!!
    }

    fun removeMedia(mediaId: Long): Long {
        val media = campaignPromotionMedia.first { it.id == mediaId }
        campaignPromotionMedia.remove(media)
        media.remove()

        return id!!
    }

    fun changeCampaign(campaignSpec: CampaignSpec): Long {
        campaignSpec.campaignDetail?.let { campaignDetail?.changeDetail(it) }
        campaignSpec.publisherLimitation?.let { publisherLimitation?.changePublisherLimitation(it) }
        campaignSpec.campaignPeriodData?.let { campaignPeriodData?.changePeriodData(it) }
        campaignSpec.campaignType?.let { campaignType?.changeType(it) }
        campaignSpec.campaignSetting?.let { campaignSetting?.changeSetting(it) }

        return id!!
    }

    fun changeStatus(status: CampaignChangeStatus): Long {
        val campaignStatusTransitionHistory = CampaignStatusTransitionHistory(
            managerId = status.managerId,
            status = campaignType!!.status,
            note = status.note,
            campaign = this
        )
        this.campaignStatusTransitionHistory.add(campaignStatusTransitionHistory)
        campaignType!!.changeStatus(status.campaignStatusCode)

        return id!!
    }
}
