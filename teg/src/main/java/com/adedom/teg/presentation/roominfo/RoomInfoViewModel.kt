package com.adedom.teg.presentation.roominfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.RoomInfoUseCase
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class RoomInfoViewModel(
    private val useCase: RoomInfoUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomInfoViewState>(RoomInfoViewState()) {

    private val channel = ConflatedBroadcastChannel<RoomInfoViewEvent>()

    var listener: RoomInfoTegMultiListener? = null

    private val _leaveRoomInfoEvent = MutableLiveData<BaseResponse>()
    val leaveRoomInfoEvent: LiveData<BaseResponse>
        get() = _leaveRoomInfoEvent

    private val _goTegMultiEvent = MutableLiveData<BaseResponse>()
    val goTegMultiEvent: LiveData<BaseResponse>
        get() = _goTegMultiEvent

    fun incomingRoomInfoTitle() {
        launch {
            setState { copy(loading = true) }

            repository.incomingRoomInfoTitle { roomInfoTitleOutgoing ->
                setState {
                    copy(
                        loading = false,
                        roomInfoTitle = roomInfoTitleOutgoing.roomInfoTitle,
                    )
                }
            }
            incomingRoomInfoTitle()
        }
    }

    fun incomingRoomInfoPlayers() {
        launch {
            setState { copy(loading = true) }

            val playerId = repository.getDbPlayerInfo()?.playerId

            repository.incomingRoomInfoPlayers { roomInfoPlayersOutgoing ->
                setState {
                    copy(
                        loading = false,
                        roomInfoPlayers = roomInfoPlayersOutgoing.roomInfoPlayers,
                        isRoleHead = useCase.isValidateRoleHead(
                            playerId,
                            roomInfoPlayersOutgoing.roomInfoPlayers
                        )
                    )
                }
            }
            incomingRoomInfoPlayers()
        }
    }

    fun incomingRoomInfoTegMulti() {
        launch {
            repository.incomingRoomInfoTegMulti { roomInfoTegMultiOutgoing ->
                listener?.roomInfoTegMultiResponse(roomInfoTegMultiOutgoing)
            }
            incomingRoomInfoTegMulti()
        }
    }

    fun callLeaveRoomInfo() {
        launch {
            setState { copy(loading = true) }

            when (val resource = useCase.callLeaveRoomInfo()) {
                is Resource.Success -> _leaveRoomInfoEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    private fun callChangeTeam(team: String) {
        launch {
            setState { copy(loading = true, isClickable = false) }

            when (val resource = useCase.callChangeTeam(team)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false, isClickable = true) }
        }
    }

    private fun callChangeGoTeg() {
        launch {
            setState { copy(loading = true, isClickable = false) }

            when (val resource = useCase.callChangeGoTeg(state.value?.roomInfoPlayers)) {
                is Resource.Success -> _goTegMultiEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false, isClickable = true) }
        }
    }

    fun callChangeStatusUnready() {
        launch {
            when (val resource = useCase.callChangeStatusUnready()) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    fun process(event: RoomInfoViewEvent) {
        launch {
            channel.send(event)
        }
    }

    init {
        channel
            .asFlow()
            .filter { state.value?.isClickable == true }
            .onEach { event ->
                when (event) {
                    is RoomInfoViewEvent.GoTeg -> callChangeGoTeg()
                    is RoomInfoViewEvent.TeamA -> callChangeTeam(TegConstant.TEAM_A)
                    is RoomInfoViewEvent.TeamB -> callChangeTeam(TegConstant.TEAM_B)
                }
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(viewModelScope)
    }

}
