package com.skplanet.shopadvisor.campaign.program.mapper

import com.skplanet.shopadvisor.campaign.program.entity.Program
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramQuery
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramSpec
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [ProgramCodeInfoMapper::class])
abstract class ProgramMapper {

    @Mapping(target = "programCode", source = "programCodeInfo")
    abstract fun toProgram(programSpec: ProgramSpec): Program

    @Mapping(target = "programCodeInfo", source = "programCode")
    abstract fun toProgramSpec(program: Program): ProgramSpec

    @Mapping(target = "programCodeInfo.categoryCode", ignore = true)
    @Mapping(target = "programCodeInfo", source = "programCode")
    abstract fun toProgramQuery(program: Program): ProgramQuery

    abstract fun toProgramQueries(programs: List<Program>): List<ProgramQuery>
}
