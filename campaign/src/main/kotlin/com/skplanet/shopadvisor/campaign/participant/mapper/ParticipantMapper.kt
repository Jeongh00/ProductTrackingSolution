package com.skplanet.shopadvisor.campaign.participant.mapper

import com.skplanet.shopadvisor.campaign.participant.entity.CampaignParticipant
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationApply
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
interface ParticipantMapper {

    fun toCampaignParticipant(campaignParticipationApply: CampaignParticipationApply): CampaignParticipant
}
