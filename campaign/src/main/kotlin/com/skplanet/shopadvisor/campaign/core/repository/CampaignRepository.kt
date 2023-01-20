package com.skplanet.shopadvisor.campaign.core.repository

import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CampaignRepository : JpaRepository<Campaign, Long>, CampaignQueryRepository {

    @Query("select c from Campaign c join fetch c.campaignPromotionMedia where c.id = :id")
    fun findPromotionFetchById(id: Long): Optional<Campaign>

    @Query("select c from Campaign c join fetch c.campaignStatusTransitionHistory where c.id = :id")
    fun findStatusHistoryFetchById(id: Long): Optional<Campaign>
}
