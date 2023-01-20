package com.skplanet.shopadvisor.campaign.acl.repository

import com.skplanet.shopadvisor.campaign.acl.entity.PublisherAccessControl
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PublisherAccessControlRepository : JpaRepository<PublisherAccessControl, Long> {

    @Query(
        """
        select
            new com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlQuery(
                pm.detail.name,
                p.restrictionSetupCode
            )
        from PublisherAccessControl p
        inner join PublisherMember pm on pm.id = p.publisherId
        where p.campaignId = :campaignId and p.deletedYn = false
    """
    )
    fun findByIdWithPublisher(campaignId: Long): List<PublisherAccessControlQuery>
}
