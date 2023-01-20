package com.skplanet.shopadvisor.campaign.participant.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAQueryBase
import com.querydsl.jpa.impl.JPAQueryFactory
import com.skplanet.shopadvisor.campaign.core.entity.QCampaign.campaign
import com.skplanet.shopadvisor.campaign.participant.entity.QCampaignParticipant.campaignParticipant
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationQuery
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationSearch
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.CampaignParticipationSearchType
import com.skplanet.shopadvisor.campaign.participant.mapper.dto.QCampaignParticipationQuery
import com.skplanet.shopadvisor.enumeration.ApprovalStatusCode
import com.skplanet.shopadvisor.enumeration.UserTypeCode
import com.skplanet.shopadvisor.member.entity.QPublisherMember.publisherMember
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import javax.persistence.EntityManager

class CampaignParticipantQueryRepositoryImpl(entityManager: EntityManager) : CampaignParticipantQueryRepository {

    private val jpaQueryFactory: JPAQueryFactory

    init
    {
        jpaQueryFactory = JPAQueryFactory(entityManager)
    }

    override fun findSearchByCondition(campaignParticipationSearch: CampaignParticipationSearch, pageable: Pageable): Page<CampaignParticipationQuery> {
        val resultQuery = jpaQueryFactory
            .select(
                QCampaignParticipationQuery(
                    campaign.id,
                    campaignParticipant.createdAt,
                    campaignParticipant.updatedAt,
                    publisherMember.memberTypeInfo.userTypeCode,
                    publisherMember.detail.loginId,
                    publisherMember.detail.name,
                    campaign.campaignDetail.name,
                    campaignParticipant.approvalStatusCode
                )
            )
            .from(campaignParticipant)
            .joinAndWhere(campaignParticipationSearch, false)
            .offset(pageable.offset).limit(pageable.pageSize.toLong())

        val countQuery = jpaQueryFactory
            .select(campaignParticipant.id.count())
            .from(campaignParticipant)
            .joinAndWhere(campaignParticipationSearch, true)

        return PageableExecutionUtils.getPage(resultQuery.fetch(), pageable) { countQuery.fetchCount() }
    }

    private fun <T, Q : JPAQueryBase<T, Q>> JPAQueryBase<T, Q>.joinAndWhere(
        campaignParticipationSearch: CampaignParticipationSearch,
        isCount: Boolean
    ): JPAQueryBase<T, Q> {
        if (!isCount || campaignParticipationSearch.publisherType != null || campaignParticipationSearch.type?.first == CampaignParticipationSearchType.PUBLISHER_NAME)
            this.innerJoin(publisherMember).on(publisherMember.id.eq(campaignParticipant.publisherId))
        if (!isCount || campaignParticipationSearch.type?.first == CampaignParticipationSearchType.CAMPAIGN_NAME)
            this.innerJoin(campaign).on(campaign.id.eq(campaignParticipant.campaignId))

        return this.where(
            isApprovalStatusCode(campaignParticipationSearch.approvalStatusCode),
            isPublisherType(campaignParticipationSearch.publisherType),
            isSearchTypePublisherName(campaignParticipationSearch.type)
        )
    }

    private fun isApprovalStatusCode(approvalStatusCode: ApprovalStatusCode?): BooleanExpression? = when (approvalStatusCode) {
        null -> null
        else -> campaignParticipant.approvalStatusCode.eq(approvalStatusCode)
    }

    private fun isPublisherType(publisherType: UserTypeCode?): BooleanExpression? = when (publisherType) {
        null -> null
        else -> publisherMember.memberTypeInfo.userTypeCode.eq(publisherType)
    }

    // TODO : 추 후 Like쿼리
    private fun isSearchTypePublisherName(pair: Pair<CampaignParticipationSearchType, String>?): BooleanExpression? {
        if (pair == null) return null

        return when (pair.first) {
            CampaignParticipationSearchType.PUBLISHER_NAME -> publisherMember.detail.name.contains(pair.second)
            CampaignParticipationSearchType.CAMPAIGN_NAME -> campaign.campaignDetail.name.contains(pair.second)
        }
    }
}
