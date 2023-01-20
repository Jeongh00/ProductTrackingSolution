package com.skplanet.shopadvisor.campaign.program.mapper

import com.skplanet.shopadvisor.campaign.program.entity.ProgramCode
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramCodeInfo
import com.skplanet.shopadvisor.enumeration.PlatformCode
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
abstract class ProgramCodeInfoMapper {

    abstract fun toProgramCode(programCodeInfo: ProgramCodeInfo): ProgramCode

    abstract fun toProgramCodeInfo(programCode: ProgramCode): ProgramCodeInfo

    fun toPlatformCode(platformCodes: List<PlatformCode>): Int {
        return PlatformCode.sumValue(platformCodes)
    }

    fun toCategoryCode(categoryCodes: List<String>): String {
        return categoryCodes.joinToString(",")
    }

    fun toPlatformCodes(platformCode: Int): List<PlatformCode> {
        return PlatformCode.getCodes(platformCode)
    }

    fun toCategoryCodes(categoryCode: String): List<String> {
        return categoryCode.split(",")
    }
}
