package com.skplanet.shopadvisor.campaign.program.entity

import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionConfigurationDto
import com.skplanet.shopadvisor.enumeration.ProvideTypeCode
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class CommissionConfiguration(
    @Column(name = "PRVD_TYP_CD")
    var provideType: ProvideTypeCode? = null,
    @Column(name = "TOT_CMMSN")
    var totalCommission: Double? = null,
    @Column(name = "OCB_CMMSN")
    var ocbCommission: Double? = null,
    @Column(name = "PCHSR_CMMSN")
    var purchaserCommission: Double? = null,
    @Column(name = "PUBL_CMMSN")
    var publisherCommission: Double? = null,
    @Column(name = "MCNT_CD")
    var nxmileMerchantCode: String? = null,
) {

    fun changeCommissionConfig(commissionConfiguration: CommissionConfigurationDto) {
        if (!commissionConfiguration.isEqualsCommission()) {
            throw IllegalArgumentException("총 합 수수료를넘을 수 없습니다.")
        }

        commissionConfiguration.provideType?.let { provideType = it }
        commissionConfiguration.ocbCommission?.let { ocbCommission = it }
        commissionConfiguration.purchaserCommission?.let { purchaserCommission = it }
        commissionConfiguration.publisherCommission?.let { publisherCommission = it }
        commissionConfiguration.nxmileMerchantCode?.let { nxmileMerchantCode = it }
    }
}
