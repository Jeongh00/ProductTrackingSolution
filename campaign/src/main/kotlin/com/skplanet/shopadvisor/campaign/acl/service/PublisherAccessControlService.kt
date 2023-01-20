package com.skplanet.shopadvisor.campaign.acl.service

import com.skplanet.shopadvisor.campaign.acl.mapper.PublisherAccessControlMapper
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlChange
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlQuery
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlRegister
import com.skplanet.shopadvisor.campaign.acl.repository.PublisherAccessControlRepository
import com.skplanet.shopadvisor.datasource.RoutingDataSource
import com.skplanet.shopadvisor.datasource.advisor.DataSourceAdvice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class PublisherAccessControlService(
    private val publisherAccessControlMapper: PublisherAccessControlMapper,
    private val publisherAccessControlRepository: PublisherAccessControlRepository
) {
    @DataSourceAdvice(RoutingDataSource.Route.SERVICE, readOnly = true)
    fun getACLs(campaignId: Long): List<PublisherAccessControlQuery> {
        return publisherAccessControlRepository.findByIdWithPublisher(campaignId)
    }

    fun registerACL(publisherAccessControlRegister: PublisherAccessControlRegister): Long {
        return publisherAccessControlRepository.save(publisherAccessControlMapper.toPublisherAccessControl(publisherAccessControlRegister)).id!!
    }

    fun registerACLs(publisherAccessControlRegisters: List<PublisherAccessControlRegister>): List<Long> {
        return publisherAccessControlRepository.saveAll(publisherAccessControlMapper.toPublisherAccessControls(publisherAccessControlRegisters)).map { it.id!! }
    }

    @Transactional
    fun changeACL(publisherAccessControlChange: PublisherAccessControlChange): Long {
        val optionalPublisherACL = publisherAccessControlRepository.findById(publisherAccessControlChange.accessControlId)

        val publisherACL = optionalPublisherACL.orElseThrow {
            throw EntityNotFoundException("Publisher Access Control엔티티를 찾을 수 없습니다")
        }

        publisherACL.changeState(publisherAccessControlChange.restrictionSetupCode)
        return publisherACL.id!!
    }

    @Transactional
    fun deleteACL(id: Long): Long {
        val pACL = publisherAccessControlRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Publisher Access Control엔티티를 찾을 수 없습니다")
        }

        return pACL.delete()
    }
}
