package com.skplanet.shopadvisor.campaign.acl.entity

import com.skplanet.shopadvisor.common.entity.BaseEntity
import com.skplanet.shopadvisor.enumeration.RestrictionSetupCode
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "publisher_acl")
class PublisherAccessControl(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "CMPGN_ID")
    var campaignId: Long,
    @Column(name = "PUBLISHER_ID")
    var publisherId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "ACS_RSTRCTN_SETUP_CD")
    var restrictionSetupCode: RestrictionSetupCode?,
    @Column(name = "DEL_YN")
    var deletedYn: Boolean = false,
) : BaseEntity() {

    fun changeState(restrictionSetupCode: RestrictionSetupCode) {
        this.restrictionSetupCode = restrictionSetupCode
    }

    fun delete(): Long {
        if (deletedYn) {
            throw IllegalStateException("이미 삭제된 Publisher Access Control입니다.")
        }

        deletedYn = true

        return id!!
    }
}
