package com.skplanet.shopadvisor.campaign.core.mapper.dto.embedded

import com.skplanet.shopadvisor.campaign.core.entity.vo.Site

data class SiteDto(
    var name: String? = null,
    var address: String? = null,
) {

    fun toSite(): Site {
        return Site(name, address)
    }
}
