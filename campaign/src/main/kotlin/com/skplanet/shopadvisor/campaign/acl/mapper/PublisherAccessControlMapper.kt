package com.skplanet.shopadvisor.campaign.acl.mapper

import com.skplanet.shopadvisor.campaign.acl.entity.PublisherAccessControl
import com.skplanet.shopadvisor.campaign.acl.mapper.dto.PublisherAccessControlRegister
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface PublisherAccessControlMapper {

    fun toPublisherAccessControl(publisherAccessControlRegister: PublisherAccessControlRegister): PublisherAccessControl

    fun toPublisherAccessControls(publisherAccessControlRegister: List<PublisherAccessControlRegister>): List<PublisherAccessControl>
}
