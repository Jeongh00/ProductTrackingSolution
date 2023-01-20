package com.skplanet.shopadvisor.campaign.core.entity.media

import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.common.entity.BaseEntity
import com.skplanet.shopadvisor.enumeration.PromotionMediaTypeCode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "campaign_promotion_media")
class CampaignPromotionMedia(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "ORIG_FILE_NAME")
    var originalFileName: String? = null,
    @Column(name = "URL")
    var url: String? = null,
    @Column(name = "PROMO_MTRL_TYP_CD")
    var type: PromotionMediaTypeCode? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMPGN_ID")
    var campaign: Campaign? = null,
    var oldCampaignId: Long? = null,
    // TODO : 소프트 딜리트용 컬럼 추가 필요
    var delete: Boolean = false
) : BaseEntity() {

    fun remove() {
        if (delete) {
            throw IllegalStateException("이미 삭제된 미디어입니다.")
        }

        delete = true
        oldCampaignId = campaign!!.id
        campaign = null
    }
}
