package com.skplanet.shopadvisor.campaign.core.service

import com.skplanet.shopadvisor.campaign.core.mapper.CampaignMapper
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignSpec
import com.skplanet.shopadvisor.campaign.core.mapper.dto.search.CampaignSearchCondition
import com.skplanet.shopadvisor.campaign.core.repository.CampaignRepository
import com.skplanet.shopadvisor.datasource.RoutingDataSource
import com.skplanet.shopadvisor.datasource.advisor.DataSourceAdvice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CampaignService(
    private val campaignMapper: CampaignMapper,
    private val campaignRepository: CampaignRepository
) {

    fun createCampaign(campaignSpec: CampaignSpec): Long {
        return campaignRepository.save(campaignMapper.toCampaign(campaignSpec)).id!!
    }

    @Transactional
    fun modifyCampaign(campaignSpec: CampaignSpec): Long {
        return CampaignServiceHelper.getCampaign(campaignRepository, campaignSpec.id!!).changeCampaign(campaignSpec)
    }

    @DataSourceAdvice(route = RoutingDataSource.Route.SERVICE, readOnly = true)
    fun inquiryDetailedCampaignWithoutImage(id: Long): CampaignSpec {
        return campaignMapper.toCampaignDto(CampaignServiceHelper.getCampaign(campaignRepository, id))
    }

    @DataSourceAdvice(route = RoutingDataSource.Route.SERVICE, readOnly = true)
    fun inquiryCampaignPageWithoutImage(campaignSearchCondition: CampaignSearchCondition, pageable: Pageable): Page<CampaignQuery> {
        return campaignRepository.findSearchByCondition(campaignSearchCondition, pageable)
    }
}
