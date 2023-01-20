package com.skplanet.shopadvisor.campaign.participant.repository

import com.skplanet.shopadvisor.campaign.participant.entity.CampaignParticipant
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationChangeQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CampaignParticipantRepository : JpaRepository<CampaignParticipant, Long>, CampaignParticipantQueryRepository {

    @Query(
        """
        select
            new com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationChangeQuery(
                m.detail.loginId,
                m.detail.name,
                c.campaignDetail.name)
        from CampaignParticipant cp
        inner join Campaign c on c.id = cp.campaignId
        inner join Member m on m.id = cp.publisherId
        where cp.id = :id
        """
    )
    fun findParticipationInfoById(id: Long): Optional<CampaignParticipationChangeQuery>

    @Query("select cp from CampaignParticipant cp where cp.campaignId = :campaignId and cp.publisherId = :publisherId")
    fun findParticipationInfoByCamIdAndPubId(campaignId: Long, publisherId: Long): Optional<CampaignParticipationChangeQuery>
}
