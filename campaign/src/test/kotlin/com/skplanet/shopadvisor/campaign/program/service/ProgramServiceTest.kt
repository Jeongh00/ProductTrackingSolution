package com.skplanet.shopadvisor.campaign.program.service

import com.skplanet.shopadvisor.campaign.CampaignAutoConfiguration
import com.skplanet.shopadvisor.campaign.program.entity.Program
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramCodeInfo
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramPickup
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramSpec
import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionConfigurationDto
import com.skplanet.shopadvisor.campaign.program.mapper.dto.embedded.CommissionPeriodDto
import com.skplanet.shopadvisor.config.QuerydslConfig
import com.skplanet.shopadvisor.enumeration.CommissionApplyTypeCode
import com.skplanet.shopadvisor.enumeration.PlatformCode
import com.skplanet.shopadvisor.enumeration.ProvideTypeCode
import com.skplanet.shopadvisor.member.ShopAdvisorMemberAutoConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime
import java.util.stream.IntStream
import javax.persistence.EntityManager

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = [CampaignAutoConfiguration::class, ShopAdvisorMemberAutoConfiguration::class, QuerydslConfig::class])
class ProgramServiceTest
@Autowired
constructor(
    private val programService: ProgramService,
    private val entityManager: EntityManager
) {

    @Test
    @DisplayName("프로그램 등록 테스트")
    fun sut_program_register_test() {
        // given
        val spec = ProgramSpec(commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0), programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.ALL, listOf(PlatformCode.IOS), listOf("Code1", "Code2")))

        // when
        val entityId = programService.registerProgram(spec)

        entityManager.flush()
        entityManager.clear()

        // then
        val entity = entityManager.find(Program::class.java, entityId)
        assertEquals(entityId, entity?.id)
        assertEquals(spec.programCodeInfo!!.categoryCode, entity.programCode!!.categoryCode.split(","))
    }

    @Test
    @DisplayName("프로그램 수정 테스트")
    fun sut_program_update_test() {
        // given
        val spec = ProgramSpec(commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0), programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.ALL, listOf(PlatformCode.IOS), listOf("Code1", "Code2")))
        val updateSpec = ProgramSpec(commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0), programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.OS, listOf(PlatformCode.AOS), listOf("Code3", "Code4")))
        val entityId = programService.registerProgram(spec)

        entityManager.flush()
        entityManager.clear()
        // when
        programService.updateProgram(entityId, updateSpec)
        val entity = entityManager.find(Program::class.java, entityId)

        // then
        assertEquals(entityId, entity?.id)
        assertNotEquals(spec.programCodeInfo!!.categoryCode, entity.programCode!!.categoryCode.split(","))
        assertEquals(updateSpec.programCodeInfo!!.categoryCode, entity.programCode!!.categoryCode.split(","))
        assertEquals(updateSpec.programCodeInfo!!.applyType, entity.programCode!!.applyType)
    }

    @Test
    @DisplayName("프로그램 조회 테스트")
    fun sut_program_inquiry_test() {
        // given
        val spec = ProgramSpec(commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0), programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.ALL, listOf(PlatformCode.IOS), listOf("Code1", "Code2")))

        // when
        val entityId = programService.registerProgram(spec)

        entityManager.flush()
        entityManager.clear()

        val query = programService.queryProgram(entityId)
        // then
        assertEquals(spec.programCodeInfo!!.applyType, query.programCodeInfo!!.applyType)
        assertEquals(spec.programCodeInfo!!.categoryCode, query.programCodeInfo!!.categoryCode)
    }

    @Test
    @DisplayName("프로그램 전체 목록 테스트")
    fun sut_program_all_inquiry_test() {
        // given
        IntStream.range(1, 5).forEach {
            val spec = ProgramSpec(
                commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0),
                campaignId = 1L,
                priority = it,
                showYN = "Y",
                programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.ALL, listOf(PlatformCode.IOS), listOf("Code1", "Code2"))
            )
            programService.registerProgram(spec)
        }

        // when
        entityManager.flush()
        entityManager.clear()

        val list = programService.queryPrograms(1L)

        // then
        assertEquals(4, list.size)
    }

    @Test
    @DisplayName("프로그램 뽑기 테스트")
    fun sut_program_pick_up_test() {
        // given
        IntStream.range(1, 5).forEach {
            val spec = if (it % 3 == 0)
                ProgramSpec(
                    campaignId = 1L,
                    priority = it,
                    showYN = "Y",
                    programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.ALL, listOf(PlatformCode.AOS), listOf("Code333", "Code2")),
                    commissionPeriod = CommissionPeriodDto(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)),
                    commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0)
                )
            else
                ProgramSpec(
                    campaignId = 1L,
                    priority = it,
                    showYN = "Y",
                    programCodeInfo = ProgramCodeInfo(CommissionApplyTypeCode.OS, listOf(PlatformCode.IOS), listOf("Code1", "Code2")),
                    commissionPeriod = CommissionPeriodDto(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)),
                    commissionConfig = CommissionConfigurationDto(ProvideTypeCode.PERCENT, 100.0, 20.0, 40.0, 40.0)
                )
            programService.registerProgram(spec)
        }

        // when
        val program = programService.pickUpProgram(ProgramPickup(1L, PlatformCode.AOS, listOf("Code333"), LocalDateTime.now()))

        // then
        assertEquals(3, program!!.priority)
        assertEquals(CommissionApplyTypeCode.ALL, program.programCodeInfo!!.applyType)
        assertTrue(program.programCodeInfo!!.platformCode!!.contains(PlatformCode.AOS))
    }
}
