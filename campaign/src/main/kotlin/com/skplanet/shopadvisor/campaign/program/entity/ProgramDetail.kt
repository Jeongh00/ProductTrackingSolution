package com.skplanet.shopadvisor.campaign.program.entity

import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.ProgramDetailDto
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class ProgramDetail(
    @Column(name = "PGM_NM")
    var name: String? = null,
    @Column(name = "PGM_CONT")
    var description: String? = null,
    @Column(name = "REMARK")
    var note: String? = null,
) {

    fun changeDetail(programDetail: ProgramDetailDto) {
        programDetail.name?.let { this.name = it }
        programDetail.description?.let { this.description = it }
        programDetail.note?.let { this.note = it }
    }
}
