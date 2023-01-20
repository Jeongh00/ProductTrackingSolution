package com.skplanet.shopadvisor.campaign.program.entity

import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramCodeInfo
import com.skplanet.shopadvisor.enumeration.CommissionApplyTypeCode
import com.skplanet.shopadvisor.enumeration.PlatformCode
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * @property categoryCode 머천트쪽과 상호협의한 뒤 발급받는 값으로 상품 카테고리 종류를 특정하는 코드값이다
 */
@Embeddable
data class ProgramCode(
    @Column(name = "CMMSN_APPL_TYP_CD")
    @Enumerated(EnumType.STRING)
    var applyType: CommissionApplyTypeCode? = null,
    @Column(name = "CMMSN_APPL_PLFM_CLSF_CD")
    var platformCode: Int? = null,
    var categoryCode: String,
) {

    fun changeCode(programCodeInfo: ProgramCodeInfo) {
        programCodeInfo.applyType?.let { applyType = it }
        programCodeInfo.platformCode?.let { platformCode = PlatformCode.sumValue(it) }
        programCodeInfo.categoryCode?.let { categoryCode = it.joinToString(",") }
    }

    fun isIncludePlatform(platformCode: PlatformCode): Boolean {
        return PlatformCode.getCodes(this.platformCode!!).any {
            it == platformCode
        }
    }

    fun isIncludeCategoryCode(categoryCodeList: List<String>): Boolean {
        return categoryCodeList.any {
            categoryCode.contains(it)
        }
    }
}
