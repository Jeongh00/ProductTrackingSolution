package com.skplanet.shopadvisor.campaign.core.entity.vo

import com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded.SiteDto
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Site(
    @Column(name = "SITE_NM")
    var name: String? = null,
    @Column(name = "SITE_ADDR")
    var address: String? = null,
) {

    fun changeSite(siteDto: SiteDto) {
        siteDto.name?.let { name = it }
        siteDto.address?.let { address = it }
    }
}
