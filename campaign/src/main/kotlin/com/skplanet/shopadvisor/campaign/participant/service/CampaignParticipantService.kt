package com.skplanet.shopadvisor.campaign.participant.service

import com.skplanet.shopadvisor.campaign.participant.mapper.ParticipantMapper
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationApply
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationChange
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationChangeQuery
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationQuery
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationSearch
import com.skplanet.shopadvisor.campaign.participant.repository.CampaignParticipantRepository
import com.skplanet.shopadvisor.datasource.RoutingDataSource
import com.skplanet.shopadvisor.datasource.advisor.DataSourceAdvice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class CampaignParticipantService(
    private val campaignMapper: ParticipantMapper,
    private val campaignParticipantRepository: CampaignParticipantRepository
) {
    @Transactional
    fun applyParticipation(campaignParticipationApply: CampaignParticipationApply): Long {
        campaignParticipantRepository.findParticipationInfoByCamIdAndPubId(campaignParticipationApply.campaignId, campaignParticipationApply.publisherId).ifPresent {
            throw IllegalArgumentException("이미 참여한 캠페인입니다.")
        }

        return campaignParticipantRepository.save(campaignMapper.toCampaignParticipant(campaignParticipationApply)).id!!
    }

    @Transactional
    @DataSourceAdvice(route = RoutingDataSource.Route.SERVICE)
    fun changeParticipation(campaignParticipationChange: CampaignParticipationChange): Long {
        val participant = campaignParticipantRepository.findById(campaignParticipationChange.participantId).orElseThrow {
            throw EntityNotFoundException("캠페인 참여자가 존재하지 않습니다.")
        }

        participant.updateStatus(campaignParticipationChange)
        return participant.id!!
    }

    @DataSourceAdvice(RoutingDataSource.Route.SERVICE, readOnly = true)
    fun showChangeQuery(id: Long): CampaignParticipationChangeQuery {
        return campaignParticipantRepository.findParticipationInfoById(id).orElseThrow { EntityNotFoundException("캠페인 참여자가 존재하지 않습니다.") }
    }

    @DataSourceAdvice(RoutingDataSource.Route.SERVICE, readOnly = true)
    fun searchParticipants(campaignParticipationSearch: CampaignParticipationSearch, pageable: Pageable): Page<CampaignParticipationQuery> {
        return campaignParticipantRepository.findSearchByCondition(campaignParticipationSearch, pageable)
    }
}
