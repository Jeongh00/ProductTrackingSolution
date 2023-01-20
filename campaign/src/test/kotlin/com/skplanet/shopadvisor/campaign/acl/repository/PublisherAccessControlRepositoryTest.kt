package com.skplanet.shopadvisor.campaign.acl.repository

import com.skplanet.shopadvisor.campaign.CampaignAutoConfiguration
import com.skplanet.shopadvisor.campaign.acl.entity.PublisherAccessControl
import com.skplanet.shopadvisor.campaign.acl.service.PublisherAccessControlService
import com.skplanet.shopadvisor.campaign.core.entity.Campaign
import com.skplanet.shopadvisor.campaign.core.repository.CampaignRepository
import com.skplanet.shopadvisor.enumeration.RestrictionSetupCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@DataJpaTest
@DisplayName("매체 접근 제어 테스트 - Repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableAutoConfiguration
@EntityScan(basePackages = ["com.skplanet.shopadvisor.**.entity"])
@ImportAutoConfiguration(CampaignAutoConfiguration::class)
@ContextConfiguration(classes = [PublisherAccessControlService::class])
class PublisherAccessControlRepositoryTest @Autowired constructor(
    val publisherAclRepository: PublisherAccessControlRepository,
    val campaignRepository: CampaignRepository,
    val entityManager: EntityManager
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    @DisplayName("캠페인 정보와 함께 fetchJoin")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun findOneByIdWithPublisher() {
        // given
        val campaign = campaignRepository.save(
            Campaign()
        )
        val accessControl = publisherAclRepository.save(
            PublisherAccessControl(
                campaignId = 1L,
                publisherId = 1L,
                restrictionSetupCode = RestrictionSetupCode.BLACK,
            )
        )

        entityManager.flush()
        entityManager.clear()

        log.info("Campaign ID: ${campaign.id}")
        log.info("Access Control ID: ${accessControl.id}")

        // whem
        val fetchedAccessControl =
            publisherAclRepository.findById(accessControl.id!!)

        fetchedAccessControl.get().campaignId
        // then
        assertEquals(true, fetchedAccessControl.isPresent)
        assertNotNull(fetchedAccessControl.get().campaignId)
        assertEquals(campaign.id, fetchedAccessControl.get().campaignId)
    }
}
