package com.skplanet.shopadvisor.campaign.program.entity.apply

import com.skplanet.shopadvisor.campaign.program.entity.Program
import com.skplanet.shopadvisor.campaign.program.mapper.dto.ProgramPickup

class CategoryCodeCommand(
    program: Program,
    programPickup: ProgramPickup
) : AbstractApplyCommand(program, programPickup) {

    override fun doInternalExecute(): Boolean {
        return program.programCode!!.isIncludeCategoryCode(programPickup.categoryCodeList)
    }
}
