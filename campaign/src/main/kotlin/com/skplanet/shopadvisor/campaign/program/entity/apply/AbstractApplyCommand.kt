package com.skplanet.shopadvisor.campaign.program.entity.apply

import com.skplanet.shopadvisor.campaign.program.entity.Program
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramPickup

abstract class AbstractApplyCommand(
    open val program: Program,
    open val programPickup: ProgramPickup
) : ApplyCommand {

    override fun execute(): Boolean {
        return program.commissionPeriod!!.isInclude(programPickup.productDateTime) && doInternalExecute()
    }

    abstract fun doInternalExecute(): Boolean
}
