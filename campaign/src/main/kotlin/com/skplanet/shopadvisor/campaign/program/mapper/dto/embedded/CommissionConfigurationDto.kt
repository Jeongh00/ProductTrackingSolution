package com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded

import com.skplanet.shopadvisor.enumeration.ProvideTypeCode

data class CommissionConfigurationDto(
    var provideType: ProvideTypeCode? = null,
    var totalCommission: Double? = null,
    var ocbCommission: Double? = null,
    var purchaserCommission: Double? = null,
    var publisherCommission: Double? = null,
    var nxmileMerchantCode: String? = null,
) {

    fun isEqualsCommission(): Boolean {
        var tempTotalCommission = 0.0

        ocbCommission?.let { tempTotalCommission += it }
        purchaserCommission?.let { tempTotalCommission += it }
        publisherCommission?.let { tempTotalCommission += it }

        return totalCommission == tempTotalCommission
    }
}
