package com.skplanet.shopadvisor.campaign.core.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAQueryBase
import com.querydsl.jpa.impl.JPAQueryFactory
import com.skplanet.shopadvisor.campaign.core.entity.QCampaign.campaign
import com.skplanet.shopadvisor.campaign.core.mapper.dto.CampaignQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.QCampaignDetailQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.QCampaignQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.QCampaignSettingQuery
import com.skplanet.shopadvisor.campaign.core.mapper.dto.search.CampaignSearchCondition
import com.skplanet.shopadvisor.campaign.core.mapper.dto.search.CampaignSearchType
import com.skplanet.shopadvisor.enumeration.CampaignStatusCode
import com.skplanet.shopadvisor.enumeration.CampaignTypeCode
import com.skplanet.shopadvisor.member.entity.QAdvertiserMember.advertiserMember
import com.skplanet.shopadvisor.support.QueryHelper.isPlatformCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class CampaignQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CampaignQueryRepository {

    override fun findSearchByCondition(campaignSearchCondition: CampaignSearchCondition, pageable: Pageable): Page<CampaignQuery> {
        val resultQuery = jpaQueryFactory
            .select(
                QCampaignQuery(
                    campaign.id, campaign.campaignType,
                    QCampaignDetailQuery(campaign.campaignDetail.name, campaign.campaignDetail.advertiserId),
                    QCampaignSettingQuery(campaign.campaignSetting.platform, campaign.campaignSetting.showYN),
                    campaign.createdAt,
                    campaign.updatedAt
                )
            )
            .from(campaign)
            .joinAndWhere(campaignSearchCondition)
            .fetch()

        val countQuery = jpaQueryFactory
            .select(campaign.count())
            .from(campaign)
            .joinAndWhere(campaignSearchCondition)
            .fetchCount()

        return PageableExecutionUtils.getPage(resultQuery, pageable) {
            countQuery
        }
    }

    private fun <T, Q : JPAQueryBase<T, Q>> JPAQueryBase<T, Q>.joinAndWhere(
        campaignSearchCondition: CampaignSearchCondition
    ): JPAQueryBase<T, Q> {
        if (campaignSearchCondition.type != null && campaignSearchCondition.type.first == CampaignSearchType.ADVERTISER_ID) {
            this.innerJoin(advertiserMember).on(advertiserMember.id.eq(campaign.campaignDetail.advertiserId))
        }

        return this.where(
            isCampaignStatus(campaignSearchCondition.state), isCampaignType(campaignSearchCondition.typeCode),
            isPlatformCode(campaignSearchCondition.platformCode, campaign.campaignSetting.platform), isShowYN(campaignSearchCondition.showYN),
            containsSearchValue(campaignSearchCondition.type)
        )
    }

    private fun isCampaignStatus(campaignStatusCode: CampaignStatusCode?): BooleanExpression? =
        campaignStatusCode?.let { campaign.campaignType.status.eq(campaignStatusCode) }

    private fun isCampaignType(campaignTypeCode: CampaignTypeCode?): BooleanExpression? =
        campaignTypeCode?.let { campaign.campaignType.type.eq(campaignTypeCode) }

    private fun isShowYN(showYN: String?): BooleanExpression? =
        showYN?.let { campaign.campaignSetting.showYN.eq(showYN) }

    // TODO : Like쿼리 최적화 필요
    private fun containsSearchValue(pair: Pair<CampaignSearchType, String>?): BooleanExpression? {
        return pair?.let {
            when (pair.first) {
                CampaignSearchType.CAMPAIGN_NAME -> campaign.campaignDetail.name.contains(pair.second)
                CampaignSearchType.ADVERTISER_ID -> advertiserMember.detail.loginId.contains(pair.second)
            }
        }
    }
}
