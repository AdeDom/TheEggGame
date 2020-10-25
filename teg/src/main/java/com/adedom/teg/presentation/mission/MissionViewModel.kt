package com.adedom.teg.presentation.mission

import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.MissionRequest
import com.adedom.teg.models.response.MissionResponse
import com.adedom.teg.presentation.usercase.MissionUseCase
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MissionViewModel(
    private val useCase: MissionUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<MissionState>(MissionState()) {

    private val channel = BroadcastChannel<MissionAction>(Channel.BUFFERED)

    fun callFetchMission() {
        launch {
            setState { copy(loading = true) }

            when (val resource = repository.callFetchMission()) {
                is Resource.Success -> setStateMission(resource)
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    private fun callMissionMain(mode: String) {
        launch {
            setState { copy(loading = true) }

            val request = MissionRequest(mode = mode)
            when (val resource = useCase.callMissionMain(request)) {
                is Resource.Success -> setStateMission(resource)
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    private fun setStateMission(resource: Resource.Success<MissionResponse>) {
        val missionInfo = resource.data.missionInfo
        setState {
            copy(
                isMissionDelivery = missionInfo?.isDelivery ?: false,
                isMissionSingle = missionInfo?.isSingle ?: false,
                isMissionMulti = missionInfo?.isMulti ?: false,
            )
        }
    }

    fun sendAction(action: MissionAction) {
        launch {
            channel.send(action)
        }
    }

    init {
        channel.asFlow()
            .onEach { action ->
                when (action) {
                    MissionAction.DELIVERY -> callMissionMain(TegConstant.MISSION_DELIVERY)
                    MissionAction.SINGLE -> callMissionMain(TegConstant.MISSION_SINGLE)
                    MissionAction.MULTI -> callMissionMain(TegConstant.MISSION_MULTI)
                }
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(viewModelScope)
    }

}
