package com.skplanet.shopadvisor.campaign.program.entity

import com.skplanet.shopadvisor.campaign.program.entity.apply.AllTypeCommand
import com.skplanet.shopadvisor.campaign.program.entity.apply.CategoryCodeCommand
import com.skplanet.shopadvisor.campaign.program.entity.apply.OsCodeCommand
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramPickup
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramSpec
import com.skplanet.shopadvisor.enumeration.CommissionApplyTypeCode
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "program")
class Program(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "CMPGN_ID")
    var campaignId: Long? = null,
    @Embedded
    var programCode: ProgramCode? = null,
    @Embedded
    var programDetail: ProgramDetail? = null,
    @Embedded
    var commissionConfig: CommissionConfiguration? = null,
    @Embedded
    var commissionPeriod: CommissionPeriod? = null,
    @Column(name = "EXP_YN")
    var showYN: String? = null,
    @Column(name = "PRTY")
    var priority: Int? = null,
) {

    fun updateSpec(programSpec: ProgramSpec): Long {
        programSpec.showYN?.let { showYN = it }
        programSpec.priority?.let { priority = it }
        programSpec.programCodeInfo?.let { programCode?.changeCode(it) }
        programSpec.programDetail?.let { programDetail?.changeDetail(it) }
        programSpec.commissionConfig?.let { commissionConfig?.changeCommissionConfig(it) }
        programSpec.commissionPeriod?.let { commissionPeriod?.changeCommissionPeriod(it) }

        return id!!
    }

    /**
     * 해당 상품이 이 프로그램에 적합한지 확인합니다.
     *
     * @param ProgramPickup
     */
    fun isApplicable(programPickup: ProgramPickup): Boolean = when (this.programCode!!.applyType) {
        CommissionApplyTypeCode.ALL -> AllTypeCommand(this, programPickup).execute()
        CommissionApplyTypeCode.CODE -> CategoryCodeCommand(this, programPickup).execute()
        CommissionApplyTypeCode.OS -> OsCodeCommand(this, programPickup).execute()
        null -> false
    }
}
