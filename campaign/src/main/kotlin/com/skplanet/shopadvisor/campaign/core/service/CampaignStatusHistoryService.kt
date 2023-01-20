package com.skplanet.shopadvisor.campaign.core.service

import com.skplanet.shopadvisor.campaign.core.mapper.CampaignMapper
import com.skplanet.shopadvisor.campaign.core.mapper.dto.status.CampaignChangeStatus
import com.skplanet.shopadvisor.campaign.core.mapper.dto.status.StatusHistoryQuery
import com.skplanet.shopadvisor.campaign.core.repository.CampaignRepository
import com.skplanet.shopadvisor.datasource.RoutingDataSource
import com.skplanet.shopadvisor.datasource.advisor.DataSourceAdvice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class CampaignStatusHistoryService(
    private val campaignMapper: CampaignMapper,
    private val campaignRepository: CampaignRepository
) {

    @Transactional
    fun changeCampaignStatus(campaignChangeStatus: CampaignChangeStatus): Long {
        return CampaignServiceHelper.getCampaign(campaignRepository, campaignChangeStatus.id).changeStatus(campaignChangeStatus)
    }

    @DataSourceAdvice(route = RoutingDataSource.Route.SERVICE, readOnly = true)
    fun inquiryStatusHistory(id: Long): List<StatusHistoryQuery> {
        return campaignMapper.toStatusHistoryListQuery(
            campaignRepository.findStatusHistoryFetchById(id).orElseThrow { throw EntityNotFoundException("Campaign을 찾을 수 없습니다.") }.campaignStatusTransitionHistory
        )
    }
}
