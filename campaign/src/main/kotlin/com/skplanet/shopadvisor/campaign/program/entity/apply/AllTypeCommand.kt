package com.skplanet.shopadvisor.campaign.program.entity.apply

import com.skplanet.shopadvisor.campaign.program.entity.Program
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramPickup

class AllTypeCommand(
    program: Program,
    programPickup: ProgramPickup
) : AbstractApplyCommand(program, programPickup) {

    override fun doInternalExecute(): Boolean {
        return program.programCode!!.isIncludePlatform(programPickup.platformCode) &&
            program.programCode!!.isIncludeCategoryCode(programPickup.categoryCodeList)
    }
}
