package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.MissionRequest
import com.adedom.teg.models.response.MissionResponse

interface MissionUseCase {

    suspend fun callMissionMain(missionRequest: MissionRequest): Resource<MissionResponse>

}
