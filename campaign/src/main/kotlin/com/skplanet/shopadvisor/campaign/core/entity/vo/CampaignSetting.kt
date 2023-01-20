package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.CampaignSettingDto
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class CampaignSetting(
    @Embedded
    var schedule: Schedule? = null,
    @Column(name = "CATGR_NM")
    var categoryName: String? = null,
    @Column(name = "PLFM_CLSF_CD")
    var platform: Int? = null,
    @Column(name = "DEEP_LINK_CRT_YN")
    var deepLinkYn: String? = null,
    @Column(name = "EXP_YN")
    var showYN: String? = null,
) {

    fun changeSetting(campaignSetting: CampaignSettingDto) {
        campaignSetting.showYN?.let { this.showYN = it }
        campaignSetting.platform?.let { this.platform = it }
        campaignSetting.categoryName?.let { this.categoryName = it }
        campaignSetting.schedule?.let { this.schedule = it.toSchedule() }
        campaignSetting.deepLinkYn?.let { this.deepLinkYn = it }
    }
}
