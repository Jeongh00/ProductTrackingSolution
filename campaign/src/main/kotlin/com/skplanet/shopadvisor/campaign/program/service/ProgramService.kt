package com.skplanet.shopadvisor.campaign.program.service

import com.skplanet.shopadvisor.campaign.program.entity.Program
import com.skplanet.shopadvisor.campaign.program.mapper.ProgramMapper
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramPickup
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramQuery
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramSpec
import com.skplanet.shopadvisor.campaign.program.repository.ProgramRepository
import com.skplanet.shopadvisor.datasource.RoutingDataSource
import com.skplanet.shopadvisor.datasource.advisor.DataSourceAdvice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class ProgramService(
    private val programMapper: ProgramMapper,
    private val programRepository: ProgramRepository
) {

    fun registerProgram(programSpec: ProgramSpec): Long {
        if (!programSpec.commissionConfig!!.isEqualsCommission()) {
            throw IllegalArgumentException("총 합 수수료를넘을 수 없습니다.")
        }

        return programRepository.save(programMapper.toProgram(programSpec)).id!!
    }

    @Transactional
    fun updateProgram(id: Long, programSpec: ProgramSpec): Long {
        return getProgram(id).updateSpec(programSpec)
    }

    @DataSourceAdvice(RoutingDataSource.Route.SERVICE, readOnly = true)
    fun queryProgram(id: Long): ProgramSpec {
        return programMapper.toProgramSpec(getProgram(id))
    }

    @DataSourceAdvice(RoutingDataSource.Route.SERVICE, readOnly = true)
    fun queryPrograms(campaignId: Long): List<ProgramQuery> {
        return programMapper.toProgramQueries(programRepository.findAllByCampaignId(campaignId))
    }

    /**
     *  등록되어있는 여러개의 캠페인 중 하나의 캠페인을 선택하여 반환한다.
     */
    @DataSourceAdvice(RoutingDataSource.Route.SERVICE, readOnly = true)
    fun pickUpProgram(programPickup: ProgramPickup): ProgramSpec? {
        val program = programRepository.findAllByCampaignId(programPickup.campaignId).firstOrNull {
            it.isApplicable(programPickup)
        }

        program.takeIf { it != null }?.let {
            return programMapper.toProgramSpec(it)
        }

        return null
    }

    private fun getProgram(id: Long): Program = programRepository.findById(id).orElseThrow {
        throw EntityNotFoundException("Program not found with id: $id")
    }
}
