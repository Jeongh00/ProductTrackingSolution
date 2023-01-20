package com.skplanet.shopadvisor.campaign.program.repository

import com.skplanet.shopadvisor.campaign.program.entity.Program
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProgramRepository : JpaRepository<Program, Long> {

    @Query("SELECT p FROM Program p WHERE p.campaignId = :campaignId order by p.priority")
    fun findAllByCampaignId(campaignId: Long): List<Program>
}
