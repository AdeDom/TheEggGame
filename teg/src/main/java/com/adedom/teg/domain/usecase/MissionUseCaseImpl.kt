package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.MissionRequest
import com.adedom.teg.models.response.MissionResponse
import com.adedom.teg.presentation.usercase.MissionUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MissionUseCaseImpl(
    private val repository: DefaultTegRepository,
) : MissionUseCase {

    override suspend fun callMissionMain(missionRequest: MissionRequest): Resource<MissionResponse> {
        return coroutineScope {
            async { repository.callMissionMain(missionRequest) }.await()
            async { repository.callFetchMission() }.await()
        }
    }

}
